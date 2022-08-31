package com.shengfq.rabbitmq.demo.simple;

import com.rabbitmq.client.Channel;
import com.shengfq.rabbitmq.demo.config.RabbitProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * ClassName: DirectConsumer
 * Description: TODO
 *
 * @author shengfq
 * @date: 2022/8/22 9:35 上午
 */
@Slf4j
@Component
@RabbitListener(queues ={RabbitProperties.DIRECT_SMS_QUEUE})
public class SmsDirectConsumer {
    final int timeout=1000;
    @Autowired
    RabbitTemplate rabbitTemplate;
    /*@Override
    public void onMessage(Message message) {
        log.info("MessageListener callback onMessage,receive message {}",message.toString());
        rabbitTemplate.receive(RabbitProperties.DIRECT_EMAIL_QUEUE,timeout);
    }*/

    @RabbitHandler
    public void receiveMessage(Object message){
        log.info("RabbitHandler 收到的消息是 message:{}",message.toString());
        rabbitTemplate.receive(RabbitProperties.DIRECT_SMS_QUEUE,timeout);
    }
    public void process(Message message, Channel channel) throws Exception {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        String msg = new String(message.getBody(),"UTF-8");
        log.info("received msg content:{}", msg);
        try {
            channel.basicAck(deliveryTag, true);
            // channel.close();
        } catch (Exception e) {
            e.printStackTrace();
            channel.basicNack(deliveryTag,true,true);
        }finally {
            channel.basicAck(deliveryTag, true);
        }
        log.info("rabbitmq consumer done!");
    }
}
