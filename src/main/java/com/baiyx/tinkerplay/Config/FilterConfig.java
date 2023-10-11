package com.baiyx.tinkerplay.Config;

import com.baiyx.tinkerplay.Other.InterceptorAndFilter.MyFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: baiyx
 * @Date: 2023年6月12日, 0012 上午 11:00:57
 * @Description: 注册过滤器配置
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<MyFilter> myFilterRegistrationBean() {
        FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new MyFilter());
        registrationBean.addUrlPatterns("/api/RedisController/getAuthCode"); // 设置过滤器的匹配路径
        registrationBean.setOrder(1); // 设置过滤器的执行顺序，可选
        return registrationBean;
    }
}
