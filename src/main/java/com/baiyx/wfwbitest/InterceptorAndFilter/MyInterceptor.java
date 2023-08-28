package com.baiyx.wfwbitest.InterceptorAndFilter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Author: baiyx
 * @Date: 2023年6月12日, 0012 上午 10:21:20
 * @Description: 自定义拦截器
 */
@Component
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 在请求处理之前执行的逻辑
        System.out.println("请求处理之前执行!");
        return true; // 返回true表示继续执行请求处理，返回false表示终止请求处理
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 在请求处理之后、视图渲染之前执行的逻辑
        System.out.println("请求处理之后、视图渲染之前执行!");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 在请求完成之后执行的逻辑，包括异常处理
        System.out.println("请求完成之后执行!");
    }
}
