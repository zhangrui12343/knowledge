package com.zr.test.demo.component.excel;

import com.zr.test.demo.model.entity.UserEntity;
import com.zr.test.demo.model.excelimport.Student;
import com.zr.test.demo.model.vo.ExcelImportVO;
import com.zr.test.demo.repository.UserDaoImpl;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class ExcelStudent {

    /**
     * 用户数据重复快速校验
     */
    private Set<String> cacheMap = new HashSet<>();
    private List<String> msg = Collections.synchronizedList(new ArrayList<>());
    /**
     * 成功的总数
     */
    private AtomicInteger success = new AtomicInteger(0);
    /**
     * 失败的总数
     */
    private AtomicInteger failed = new AtomicInteger(0);

    private List<String> rows = Collections.synchronizedList(new ArrayList<>());
    public ExcelImportVO handleImport(List<Student> datas, UserDaoImpl gUserDao) {
        UserEntity userEntity=new UserEntity();
        userEntity.setStudent(1);
        List<UserEntity> users=  gUserDao.selectByEntity(userEntity);

        if(!ListUtil.isEmpty(users)){
            cacheMap.addAll(users.stream().map(UserEntity::getStudentNo).collect(Collectors.toList()));
        }
        //获取读取数据的大小
        int size = datas.size();
        datas = datas.subList(0, size);
        //是否是异步，异步则对源数据进行切割
        size = 1000;
        //切割数据
        log.info("{}",datas.size());
        List<? extends Object[]> dataList = ListUtil.listToArray(datas, size);
        log.info("{}",dataList.size());
        //核心线程数，如果小10，则世界使用分组的list大小
        int coreThreads = Math.min(dataList.size(), 10);
        //创建一个线程池
        ThreadPoolExecutor threadPool = this.getThreadPool(coreThreads, dataList.size());
        //创建一个主线程等待所有的子线程结束，唤醒后面的程序
        final CountDownLatch cdOrder = new CountDownLatch(1);
        //创建多个子线程执行任务，当任务执行完毕以后cutDown一次，知道变为0,主线程停止等待
        final CountDownLatch cdAnswer = new CountDownLatch(dataList.size());
        log.info("批量导入：需要新建线程的个数：" + dataList.size());
        String now = TimeUtil.getTime();
        //批量导入

        for (Object[] vos : dataList) {
            Runnable runnable = () -> {
                log.info("线程" + Thread.currentThread().getName() + "开始导入");
                String row ;
                for (Object voo : vos) {
                    try {
                        Student vo = (Student) voo;
                        row=vo.getStudentNo();
                        log.info("线程： thread = {} 开始导入", Thread.currentThread().getName());
                        //校验数据库是否存在 - 目前校验的方式比较简单，是根据唯一的key 和 值的进行校验
                        if(cacheMap.contains(vo.getStudentNo())){
                            //已经插入了
                            msg.add("学籍号:"+row+" 已经存在");
                            rows.add(row);
                            failed.getAndIncrement();
                            continue;
                        }
                        //行数据是否校验有效
                        try {
                            //插入数据
                            UserEntity entity=new UserEntity();
                            entity.setStudentNo(vo.getStudentNo());
                            entity.setName(vo.getName());
                            entity.setStudent(1);
                            entity.setIntranet(1);
                            entity.setRegister(now);
                            entity.setStatus(1);
                            entity.setSchool(vo.getSchool());
                            gUserDao.insertOne(entity);
                            if (cacheMap != null) {
                                cacheMap.add(entity.getStudentNo());
                            }
                            success.getAndIncrement();
                        } catch (Exception e) {
                            String err = e.getMessage();
                            String message;
                            if (err.contains("Duplicate entry")) {
                                message = "学籍号: " + row + " 数据已存在";
                            } else if (err.contains("SQLException")) {
                                int index = err.lastIndexOf("SQLException");
                                message = "学籍号: " + row + "  数据插入出错：" + err.substring(index);
                                log.error("导入插入数据库出错：" + e.getMessage(), e);
                            } else {
                                message = "学籍号: " + row + "，服务器插入数据出错:" + e.getMessage();
                                log.error("导入插入数据库出错：" + e.getMessage(), e);
                            }
                            rows.add(row);
                            failed.getAndIncrement();
                            msg.add(message);
                        }
                    } catch (Exception e) {
                        log.error("导入执行子线程任务出错：" + e.getMessage(), e);
                        msg.add("导入出错..");
                        failed.getAndIncrement();
                    }
                }
                try {
                    //任务执行完毕，返回给主线程，cdAnswer减1。
                    cdAnswer.countDown();
                } catch (Exception e) {
                    log.error("导入子任务计数器错误：" + e.getMessage(), e);
                }
            };
            //为线程池添加任务
            threadPool.execute(runnable);
        }
        try {
            //主任务执行完毕，子任务线程开始作业
            cdOrder.countDown();
            //主线程阻塞等地子线程执行任务
            cdAnswer.await();
        } catch (Exception e) {
            log.error("导入主线程的计数器错误：" + e.getMessage(), e);
        }
        //关闭线程池，并摧毁所有的线程
        threadPool.shutdownNow();
        ExcelImportVO importVO = new ExcelImportVO();
        importVO.setFailed(failed.get());
        importVO.setSuccess(success.get());
        importVO.setRows(rows);
        importVO.setErrorInfo(msg);
        return importVO;
    }

    /**
     * 获取线程池
     * <p>
     * AbortPolicy()            当线程池中的数量等于最大线程数时、直接抛出抛出java.util.concurrent.RejectedExecutionException异常
     * CallerRunsPolicy()       当线程池中的数量等于最大线程数时、重试执行当前的任务，交由调用者线程来执行任务
     * DiscardOldestPolicy()    当线程池中的数量等于最大线程数时、抛弃线程池中最后一个要执行的任务，并执行新传入的任务
     * DiscardPolicy()          当线程池中的数量等于最大线程数时，不做任何动作
     *
     * @param core      核心线程数
     * @param queueSize 缓存任务的队列最大值
     * @return 线程池
     */
    private ThreadPoolExecutor getThreadPool(int core, int queueSize) {
        return new ThreadPoolExecutor(
                core,
                core,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queueSize),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }

}
