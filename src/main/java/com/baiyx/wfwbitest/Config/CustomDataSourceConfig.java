package com.baiyx.wfwbitest.Config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @Author: baiyx
 * @Date: 2023年8月21日, 0021 上午 8:37:43
 * @Description: 自定义DataSource的bean
 */
@Configuration
public class CustomDataSourceConfig {
    @Bean(name = "primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
    public DataSource primaryDataSource() {
        return new DriverManagerDataSource();
    }
}
