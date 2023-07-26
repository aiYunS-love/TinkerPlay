package com.baiyx.wfwbitest.InterceptorAndFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @Author: baiyx
 * @Date: 2023年6月12日, 0012 上午 10:49:13
 * @Description: 自定义过滤器
 */
public class MyFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(MyFilter.class);

    // 可选的初始化方法
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("请求处理");
        System.out.println("过滤器初始化方法执行!");
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 执行过滤逻辑
        logger.info("请求处理");
        System.out.println("Request received: " + request.getRemoteAddr());
        // 继续处理请求
        chain.doFilter(request, response);
    }

    // 可选的销毁方法
    @Override
    public void destroy() {
        logger.info("销毁");
        System.out.println("过滤器销毁方法执行!");
        Filter.super.destroy();
    }
}
