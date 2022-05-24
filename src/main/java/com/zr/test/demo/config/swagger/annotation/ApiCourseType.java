package com.zr.test.demo.config.swagger.annotation;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SwaggerApiGroup(value = "CourseTypeApi")
public @interface ApiCourseType {
    String value() default "CourseTypeApi";
}
