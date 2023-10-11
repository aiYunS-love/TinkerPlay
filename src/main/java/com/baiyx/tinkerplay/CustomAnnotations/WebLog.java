package com.baiyx.tinkerplay.CustomAnnotations;

import java.lang.annotation.*;

/**
 * @Author: baiyx
 * @Date: 2022-7-27 下午 12:00
 * @Description: 自定义注解-日志格式术输出
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface WebLog {

    /**
    * @Author: baiyx
    * @Description: 日志输出
    * @Date: 2022-7-27 下午 12:00
    * @Param: null
    * @return:  null
    */
    String description() default "";
}
