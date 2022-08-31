package com.shengfq.rabbitmq.demo.worker;

import com.rabbitmq.client.Channel;
import com.shengfq.rabbitmq.demo.config.MessageStruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ClassName: MailMessageListenerAdapter
 * Description: 邮件队列消费者
 *
 * @author shengfq
 * @date: 2022/8/23 3:41 下午
 */
@Slf4j
@Component("mailMessageListenerAdapter")
public class MailMessageListenerAdapter extends MessageListenerAdapter {
    @Override
    public void onMessage(Message message, Channel channel) {
        try {
            // 解析RabbitMQ消息体
            String messageBody = new String(message.getBody());
            log.info("receive message:{}",messageBody);
            //模拟业务处理 反序列化成对象,进行业务逻辑处理
            Thread.sleep(1000);
            //TODO 手动ACK
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
