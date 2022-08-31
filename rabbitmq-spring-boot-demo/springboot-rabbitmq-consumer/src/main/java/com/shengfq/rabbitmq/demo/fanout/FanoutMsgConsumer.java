package com.shengfq.rabbitmq.demo.fanout;

import com.shengfq.rabbitmq.demo.config.RabbitProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ClassName: FanoutMsgConsumer
 * Description: 广播消息接收器
 *
 * @author shengfq
 * @date: 2022/8/19 9:45 下午
 */
@Slf4j
@Component
@RabbitListener(queues ={RabbitProperties.FANOUT_EMAIL_QUEUE,RabbitProperties.FANOUT_WX_QUEUE,RabbitProperties.FANOUT_SMS_QUEUE})
public class FanoutMsgConsumer {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @RabbitHandler
    public void receiveMessage(Object message){
        log.info("收到的消息是 message:{}",message.toString());
    }
}
