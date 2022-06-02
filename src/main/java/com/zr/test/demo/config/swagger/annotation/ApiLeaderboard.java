package com.zr.test.demo.config.swagger.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SwaggerApiGroup(value = "首页排行榜api")
public @interface ApiLeaderboard {
    String value() default "首页排行榜api";
}
