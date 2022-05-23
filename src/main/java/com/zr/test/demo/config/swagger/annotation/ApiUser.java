package com.zr.test.demo.config.swagger.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SwaggerApiGroup(value = "userApi")
public @interface ApiUser {

}
