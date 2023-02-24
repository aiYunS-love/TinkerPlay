package com.baiyx.wfwbitest.Config;

import com.baiyx.wfwbitest.Enum.QueueEnum;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
* @Author: 白宇鑫
* @Description: 用于配置交换机、队列及队列与交换机的绑定关系
* @Date: 2022-9-1 下午 03:34
*/

@Configuration
public class RabbitMqConfig {
    /**
     * 订单消息实际消费队列所绑定的交换机
     */
    @Bean
    DirectExchange orderDirect() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(QueueEnum.EXCHANGE_QUEUE_Aa.getExchange())
                // 持久化配置
                .durable(true)
                .build();
    }

    /**
     * 订单延迟队列队列所绑定的交换机
     */
    @Bean
    DirectExchange orderTtlDirect() {
        return (DirectExchange) ExchangeBuilder
                .directExchange(QueueEnum.EXCHANGE_QUEUE_Bb.getExchange())
                .durable(true)
                .build();
    }

    /**
     * 订单实际消费队列
     */
    @Bean
    public Queue orderQueue() {
        return new Queue(QueueEnum.EXCHANGE_QUEUE_Aa.getName());
    }

    /**
     * 订单延迟队列（死信队列）
     */
    @Bean
    public Queue orderTtlQueue() {
        return QueueBuilder
                .durable(QueueEnum.EXCHANGE_QUEUE_Bb.getName())
                .withArgument("x-dead-letter-exchange", QueueEnum.EXCHANGE_QUEUE_Aa.getExchange())//到期后转发的交换机
                .withArgument("x-dead-letter-routing-key", QueueEnum.EXCHANGE_QUEUE_Aa.getRouteKey())//到期后转发的路由键
                .build();
    }

    /**
     * 将订单队列绑定到交换机
     */
    @Bean
    Binding orderBinding(DirectExchange orderDirect,Queue orderQueue){
        return BindingBuilder
                .bind(orderQueue)
                .to(orderDirect)
                .with(QueueEnum.EXCHANGE_QUEUE_Aa.getRouteKey());
    }

    /**
     * 将订单延迟队列绑定到交换机
     */
    @Bean
    Binding orderTtlBinding(DirectExchange orderTtlDirect,Queue orderTtlQueue){
        return BindingBuilder
                .bind(orderTtlQueue)
                .to(orderTtlDirect)
                .with(QueueEnum.EXCHANGE_QUEUE_Bb.getRouteKey());
    }
}
