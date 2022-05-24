package com.zr.test.demo.config.swagger.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SwaggerApiGroup(value = "文件管理api")
public @interface ApiFile {
    String value() default "文件管理api";
}
