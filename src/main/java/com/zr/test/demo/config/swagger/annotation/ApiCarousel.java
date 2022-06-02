package com.zr.test.demo.config.swagger.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SwaggerApiGroup(value = "首页轮播图api")
public @interface ApiCarousel {
    String value() default "首页轮播图api";
}
