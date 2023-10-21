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
public class XRabbitConfig {
  public static final String QUEUE_NAME = "javaboy_delay_queue";
  public static final String EXCHANGE_NAME = "javaboy_delay_exchange";
  public static final String EXCHANGE_TYPE = "x-delayed-message";

  @Bean
  public RabbitTemplate rabbitTemplate(CachingConnectionFactory connectionFactory) {
    connectionFactory.setPublisherConfirms(true);
    connectionFactory.setPublisherReturns(true);
    RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMandatory(true);

    rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> log.info(
        "消息接收确认成功:消息:correlationData({}),响应:ack({}),原因:cause({})", correlationData, ack, cause));
    // 返回值发送给 Client 端
    rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
      log.info("消息丢失:exchange({}),route({}),replyCode({}),replyText({}),message:{}", exchange,
          routingKey, replyCode, replyText, message);
      // TODO 一般都是记日志,邮件,落库
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
