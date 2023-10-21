package com.shengfq.rabbitmq.demo.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.shengfq.rabbitmq.demo.config.XRabbitConfig;

import lombok.extern.slf4j.Slf4j;

/**
 * 延迟消息
 */
@Slf4j
@Component
public class DelayMsgReceiver {
  @RabbitListener(queues = XRabbitConfig.QUEUE_NAME)
  public void handleMsg(String msg) {
    log.info("handleMsg,{}", msg);
  }
}
