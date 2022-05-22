package com.zr.test.demo.component.listener;

import com.zr.test.demo.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 注册表时间监听
 * 启动的时候写入注册表时间
 *
 * @author zhangrui
 * @date 2022/3/1 0003 17:34
 */
@Slf4j
@Component
public class StartServerListener implements ApplicationListener<ContextRefreshedEvent>, Ordered {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("{} StartServerListener starting", TimeUtil.getTime());
//        File f=new File(fileSavePath+"/course/");
//        File f2=new File(fileSavePath+"/after_course/");
//        File f3=new File(fileSavePath+"/tercher_course/");
//        File f4=new File(fileSavePath+"/topic/");
//        if(!f.exists()) f.mkdir();
//        if(!f2.exists()) f2.mkdir();
//        if(!f3.exists()) f3.mkdir();
//        if(!f4.exists()) f4.mkdir();
    }

    @Override
    public int getOrder() {
        return 1;
    }
}

