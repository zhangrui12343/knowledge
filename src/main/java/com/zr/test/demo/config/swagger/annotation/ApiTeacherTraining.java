package com.zr.test.demo.config.swagger.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SwaggerApiGroup(value = "教师研修api")
public @interface ApiTeacherTraining {
    String value() default "教师研修api";
}
