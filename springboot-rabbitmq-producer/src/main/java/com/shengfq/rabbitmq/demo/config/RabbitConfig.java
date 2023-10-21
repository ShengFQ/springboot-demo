package com.shengfq.rabbitmq.demo.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

/**
 * RabbitMQ配置，主要是配置队列，如果提前存在该队列，可以省略本配置类
 **/
@Slf4j
@Configuration
public class RabbitConfig {

  public static final String QUEUE_NAME = "javaboy_delay_queue";
  public static final String EXCHANGE_NAME = "javaboy_delay_exchange";
  public static final String EXCHANGE_TYPE = "x-delayed-message";


  @Bean
  public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
    connectionFactory.setPublisherConfirms(true);
    connectionFactory.setPublisherReturns(true);
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    // 设置所有的发送者mandatory=true,表示如果投递失败则会将消息返回给发送者
    rabbitTemplate.setMandatory(true);
    // producer消息确认机制 这个接口是用来确定消息是否到达交换器的
    rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
      if (ack) {
        log.info("消息投递成功~消息Id：{}", correlationData.getId());
      } else {
        log.error("消息投递失败，Id：{}，错误提示：{}", correlationData.getId(), cause);
      }
    });
    // 生产者获取到没有被正确路由到合适队列的消息
    rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
      log.info("消息没有路由到队列，获得返回的消息");
      log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange,
          routingKey, replyCode, replyText, message);
    });
    return rabbitTemplate;
  }

  @Bean
  Queue queue() {
    return new Queue(QUEUE_NAME, true, false, false);
  }

  @Bean
  CustomExchange customExchange() {
    Map<String, Object> args = new HashMap<>();
    args.put("x-delayed-type", "direct");
    return new CustomExchange(EXCHANGE_NAME, EXCHANGE_TYPE, true, false, args);
  }

  @Bean
  Binding binding() {
    return BindingBuilder.bind(queue()).to(customExchange()).with(QUEUE_NAME).noargs();
  }

}
