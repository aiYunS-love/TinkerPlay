package com.baiyx.wfwbitest;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

//@EnableRabbit
@EnableCaching
@SpringBootApplication
public class WfwBiTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WfwBiTestApplication.class, args);
        // 启动打开默认浏览器访问
        try {
            Runtime.getRuntime().exec("cmd /c start http://localhost:9090/api/upload.html");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
