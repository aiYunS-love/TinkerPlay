package com.baiyx.wfwbitest.Rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.stereotype.Component;

/**
 * @Author: 白宇鑫
 * @Date: 2022-9-1 下午 03:31
 * @Description: 编写消费者代码
 * 1.消费者连接到RabbitMQ Broker ，建立一个连接(Connection ) ，开启一个信道(Channel) 。
 * 2.消费者向RabbitMQ Broker 请求消费相应队列中的消息，可能会设置相应的回调函数， 以及做一些准备工作
 * 3.等待RabbitMQ Broker 回应并投递相应队列中的消息， 消费者接收消息。
 * 4.消费者确认( ack) 接收到的消息。
 * 5.RabbitMQ 从队列中删除相应己经被确认的消息。
 * 6.关闭信道。
 * 7.关闭连接。
 */
@Component
public class DirectConsumer {

    @RabbitHandler
    //@RabbitListener(queues ="baiyxTestRabbitMQ" )
    public void  process(Message message){

        String test = new  String(message.getBody());
        System.out.println(test);
    }
}
