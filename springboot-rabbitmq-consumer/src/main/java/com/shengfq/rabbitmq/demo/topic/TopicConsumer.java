package com.shengfq.rabbitmq.demo.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import static com.shengfq.rabbitmq.demo.config.RabbitProperties.REAL_CONSUMER_QUEUE;

/**
 * ClassName: TopicConsumer
 * Description: TODO
 *
 * @author shengfq
 * @date: 2022/8/22 11:33 上午
 */
@Slf4j
@Service
@RabbitListener(queues = {REAL_CONSUMER_QUEUE})
public class TopicConsumer {

    @RabbitHandler
    public void receiveMessage(Object message){
        log.info("receive queue :{} message:{}",REAL_CONSUMER_QUEUE,message.toString());
        log.info("收到消息,将过期未支付的订单进行失效处理");
    }
}
