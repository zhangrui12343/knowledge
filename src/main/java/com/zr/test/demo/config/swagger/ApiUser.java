package com.zr.test.demo.config.swagger;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SwaggerApiGroup(value = "userApi")
public @interface ApiUser {

}
