package com.zr.test.demo.config.swagger;

import java.lang.annotation.*;

/**
 * swagger2的配置
 * @author huang_kangjie
 * @date 2020/6/3 0003 19:29
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface SwaggerApiGroup {

     String value() default "";

}
