package com.zr.test.demo.component.log;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogPrint {

    /**
     * 是否打印结果日志
     * @return 默认返回true（打印）
     */
    boolean print() default true;

    /**
     * 是否打印参数日志
     * @return 默认返回true（打印）
     */
    boolean printParam() default false;
}
