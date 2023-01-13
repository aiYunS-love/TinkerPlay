package com.baiyx.wfwbitest.Rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Message;
import javax.annotation.Resource;

/**
 * @Author: 白宇鑫
 * @Date: 2022-9-1 下午 03:35
 * @Description: 编写生产者代码
 * 1.生产者连接RabbitMQ，建立TCP连接( Connection)，开启信道(Channel)
 * 2.生产者声明一个Exchange(交换器)，并设置相关属性，比如交换器类型、是否持久化等
 * 3.生产者声明一个队列井设置相关属性，比如是否排他、是否持久化、是否自动删除等
 * 4.生产者通过 bindingKey (绑定Key)将交换器和队列绑定( binding )起来
 * 5.生产者发送消息至RabbitMQ Broker，其中包含 routingKey (路由键)、交换器等信息
 * 6.相应的交换器根据接收到的 routingKey 查找相匹配的队列。
 * 7.如果找到，则将从生产者发送过来的消息存入相应的队列中。
 * 8.如果没有找到，则根据生产者配置的属性选择丢弃还是回退给生产者
 * 9.关闭信道。
 * 10.关闭连接。
 */
@Component
public class DirectProvider {
    @Resource
    private RabbitTemplate rabbitTemplate;
    public  void  send(int i){
        Message  message = new Message(("RabbitMQ" + i).getBytes());
        rabbitTemplate.send("baiyxTestRabbitMQ" ,"baiyxTestRabbitMQ",message);
    }
}
