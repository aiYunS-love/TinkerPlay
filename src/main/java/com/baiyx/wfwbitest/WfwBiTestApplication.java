package com.baiyx.wfwbitest;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

//@EnableRabbit
@EnableCaching
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class WfwBiTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WfwBiTestApplication.class, args);
    }

}
