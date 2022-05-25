package com.zr.test.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class webMvcConfig  implements WebMvcConfigurer {
    // 定义在application.properties
    @Value("${file.save.path}")
    private String path = "upload/";
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String p = new File(path).getAbsolutePath() + File.separator;//取得在服务器中的绝对路径
        registry.addResourceHandler("/upload/**") // 外部访问地址
                .addResourceLocations("file:" + p)// springboot需要增加file协议前缀
                .setCacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES));// 设置浏览器缓存30分钟
    }

}
