package com.baiyx.wfwbitest.Rabbitmq;

import com.baiyx.wfwbitest.Entity.QueryRequestVo;
import com.baiyx.wfwbitest.Service.UserService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: 白宇鑫
 * @Date: 2022-9-1 下午 03:31
 * @Description: 取消订单消息的接收者CancelOrderReceiver
 *               取消订单消息的处理者
 */
@Component
@RabbitListener(queues = "mall.order.cancel")
public class CancelOrderReceiver {

    private static Logger LOGGER =LoggerFactory.getLogger(CancelOrderReceiver.class);
    @Autowired
    private UserService userService;
    @RabbitHandler
    public void handle(QueryRequestVo username){
        LOGGER.info("receive delay message username:{}",username);
        userService.deleteByName(username);
    }
}
