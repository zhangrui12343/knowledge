package com.zr.test.demo.service.impl;

import com.zr.test.demo.common.Result;
import com.zr.test.demo.component.exception.CustomException;
import com.zr.test.demo.config.enums.ErrorCode;
import com.zr.test.demo.model.entity.FileRouter;
import com.zr.test.demo.dao.FileRouterMapper;
import com.zr.test.demo.service.IFileRouterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.test.demo.util.FileUtil;
import com.zr.test.demo.util.ListUtil;
import com.zr.test.demo.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zr
 * @since 2022-05-21
 */
@Service
@Slf4j
public class FileRouterServiceImpl extends ServiceImpl<FileRouterMapper, FileRouter> implements IFileRouterService {
    @Value("${file.save.path}")
    private String fileSavePath;
    private String path = fileSavePath + "files/";

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Object> upload(MultipartFile[] files,List<Long> old) {
        delete(old);
        if (files.length == 0) {
            throw new CustomException(ErrorCode.SYS_PARAM_ERR);
        }
        List<Long> ids = new ArrayList<>();
        File p = new File(path);
        if (!p.exists()) {
            p.mkdir();
        }
        for (MultipartFile file : files) {
            if (FileUtil.isEmpty(file)) {
                continue;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            Date n = new Date();
            String now = sdf.format(n);
            String filePath;
            try {
                filePath = path + now + "-" + file.getOriginalFilename();
                file.transferTo(new File(filePath));
            } catch (IOException e) {
                log.info("上传文件失败 filename={}  time={}", file.getOriginalFilename(), now);
                log.error("{}", e.getMessage(), e);
                throw new CustomException(ErrorCode.FILE_UPLOAD_FAIL);
            }
            FileRouter fileRouter = new FileRouter();
            fileRouter.setFilePath(filePath);
            fileRouter.setCreateTime(n);
            this.getBaseMapper().insert(fileRouter);
            ids.add(fileRouter.getId());
        }
        return Result.success(ids);
    }

    private void delete( List<Long> ids) {
        if(ListUtil.isEmpty(ids)){
            return ;
        }
        for (Long id : ids) {
            FileRouter fileRouter = this.getBaseMapper().selectById(id);
            if (fileRouter != null) {
                File file = new File(fileRouter.getFilePath());
                if (file.exists() ) {
                    if(file.delete()){
                        log.info("文件删除成功,删除数据库记录,path={},id={}",fileRouter.getFilePath(),id);
                        this.getBaseMapper().deleteById(id);
                    }else{
                        log.error("文件删除失败,path={},id={}",fileRouter.getFilePath(),id);
                    }
                }
            }
        }
    }
}
