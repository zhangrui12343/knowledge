package com.zr.test.demo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.concurrent.TimeUnit;
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${file.save.path}")
    private String path = "../uploads/";
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //取得在服务器中的绝对路径
        String p = new File(path).getAbsolutePath() + File.separator;
        // 外部访问地址
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + p)
                .setCacheControl(CacheControl.maxAge(30, TimeUnit.MINUTES));
    }

}
