package com.shengfq.rabbitmq.demo;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.junit.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shengfq.rabbitmq.demo.config.XRabbitConfig;

@SpringBootTest
class MqDelayedMsgDemoApplicationTests {

  @Autowired
  RabbitTemplate rabbitTemplate;

  @Test
  void contextLoads() throws UnsupportedEncodingException {
    Message msg = MessageBuilder.withBody(("delay message" + new Date()).getBytes("UTF-8"))
        .setHeader("x-delay", 3000).build();
    rabbitTemplate.convertAndSend(XRabbitConfig.EXCHANGE_NAME, XRabbitConfig.QUEUE_NAME, msg);
  }

}
