package com.baiyx.wfwbitest.Config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
* @Author: 白宇鑫
* @Description: 配置交换机，队列和bindingKey
* @Date: 2022-9-1 下午 03:34
*/

@Configuration
public class RabbitConfig {
    @Bean
    public DirectExchange directExchange(){
        return  new DirectExchange("baiyxTestRabbitMQ",true,false);
    }

    @Bean
    public Queue directQueue(){
        return   QueueBuilder.durable("baiyxTestRabbitMQ").build();
    }

    @Bean
    public Binding binding(){
        return  BindingBuilder.bind(directQueue()).to(directExchange()).with("baiyxTestRabbitMQ");
    }
}
