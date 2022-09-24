package com.baiyx.wfwbitest.customAnnotations;

import java.lang.annotation.*;

/**
 * @Author: 白宇鑫
 * @Date: 2022-9-13 上午 11:23
 * @Description: 自定义解密注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.PARAMETER})
@Documented
public @interface Decrypt {

    String description() default "";
}
