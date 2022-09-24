package com.baiyx.wfwbitest.aop;

import com.baiyx.wfwbitest.customAnnotations.NullDisable;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Author: 白宇鑫
 * @Date: 2022-8-29 下午 02:36
 * @Description:
 */
@Aspect
@Component
public class ValidParameterAspect {
    //com.baiyx.wfwbitest.controller包下所有的类
    @Pointcut("execution(* com.baiyx.wfwbitest.controller..*.*(..)))")
    public void valid() {};

    @Around("valid()")
    public Object check(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        //获得参数类型
        final Parameter[] parameters = method.getParameters();
        //参数值
        final Object[] args = joinPoint.getArgs();
        //参数名称
        String[] names = signature.getParameterNames();


        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Object annotation = parameter.getAnnotation(NullDisable.class);
            //含有不为空的注解的参数
            if (null != annotation) {
                if (null == args[i]) {
                    throw new ParamException(String.format("参数:%s,不能为空", names[i]));
                }
            }

        }
        return joinPoint.proceed();
    }
}
