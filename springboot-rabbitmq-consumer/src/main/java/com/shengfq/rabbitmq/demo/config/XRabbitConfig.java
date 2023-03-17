package com.shengfq.rabbitmq.demo.config;

import com.shengfq.rabbitmq.demo.worker.MailMessageListenerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *RabbitMQ配置，主要是配置队列，如果提前存在该队列，可以省略本配置类
 **/
@Slf4j
@Configuration
public class XRabbitConfig {
    @Bean
    public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.info("消息接收确认成功:消息:correlationData({}),响应:ack({}),原因:cause({})", correlationData, ack, cause));
        //返回值发送给 Client 端
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange, routingKey, replyCode, replyText, message);
            //TODO 消息要重发 消息的持久化处理
        });
        return rabbitTemplate;
    }
    /*@Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        log.info("初始化 myContainerFactory bean");
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        //此处也设置为手动ack
        factory.setConnectionFactory(connectionFactory);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //factory.setConnectionFactory(connectionFactory);
        //factory.setConcurrentConsumers(3);
        // factory.setMaxConcurrentConsumers(10);
        return factory;
    }*/

    /**
     * 使用非注解方式注册队列监听器
     *
     * */
    @Bean
    public SimpleMessageListenerContainer listenerContainer(
            @Qualifier("mailMessageListenerAdapter") MailMessageListenerAdapter mailMessageListenerAdapter,CachingConnectionFactory connectionFactory) throws Exception {
        SimpleMessageListenerContainer simpleMessageListenerContainer =
                new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.setQueueNames(RabbitProperties.FANOUT_EMAIL_QUEUE);
        simpleMessageListenerContainer.setMessageListener(mailMessageListenerAdapter);
        // 设置手动 ACK
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return simpleMessageListenerContainer;
    }

}
