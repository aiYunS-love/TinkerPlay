package com.baiyx.wfwbitest.customAnnotations;

import java.lang.annotation.*;

/**
 * @Author: 白宇鑫
 * @Date: 2022-8-29 下午 02:28
 * @Description: 自定义参数判空的注解
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.PARAMETER})
public @interface NullDisable {
    String description() default "";
}
