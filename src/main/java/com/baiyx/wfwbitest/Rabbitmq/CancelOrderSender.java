package com.baiyx.wfwbitest.Rabbitmq;

import com.baiyx.wfwbitest.Entity.QueryRequestVo;
import com.baiyx.wfwbitest.Enum.QueueEnum;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

/**
 * @Author: 白宇鑫
 * @Date: 2022-9-1 下午 03:35
 * @Description: 延迟消息的发送者CancelOrderSender
 *               取消订单消息的发出者
 */
@Component
public class CancelOrderSender {
    private static Logger LOGGER =LoggerFactory.getLogger(CancelOrderSender.class);
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(String queryRequestVo, final long delayTimes){
        //给延迟队列发送消息
        amqpTemplate.convertAndSend(QueueEnum.EXCHANGE_QUEUE_Bb.getExchange(), QueueEnum.EXCHANGE_QUEUE_Bb.getRouteKey(), queryRequestVo.toString(), new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置延迟毫秒值
                message.getMessageProperties().setExpiration(String.valueOf(delayTimes));
                return message;
            }
        });
        LOGGER.info("send delete message queryRequestVo:{}",queryRequestVo);
    }
}
