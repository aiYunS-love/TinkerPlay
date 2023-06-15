package com.baiyx.wfwbitest.Rabbitmq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: 白宇鑫
 * @Date: 2023年6月15日, 0015 上午 10:33:23
 * @Description: MQ广播消息发送者
 */
@Component
public class BroadcastMessageSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendBroadcastMessage(String message, final long delayTimes) {
        rabbitTemplate.convertAndSend("exchange_broadcast", "", message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给广播设置延迟毫秒值
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                System.out.println("设置延迟时间完成!");
                return message;
            }
        });
        System.out.println("广播消息发送完成!");
    }
}
