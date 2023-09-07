//package com.baiyx.wfwbitest.Config;
//
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//
//import javax.sql.DataSource;
//
///**
// * @Author: baiyx
// * @Date: 2023年8月21日, 0021 上午 8:37:43
// * @Description: 自定义DataSource的bean
// *               自定义的数据源bean会覆盖baomidou的数据源bean,导致多数据源不生效, 所以暂停使用
// */
//@Configuration
//public class CustomDataSourceConfig {
//    @Bean(name = "primaryDataSource")
//    @Primary
//    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
//    public DataSource primaryDataSource() {
//        return new DriverManagerDataSource();
//    }
//}
