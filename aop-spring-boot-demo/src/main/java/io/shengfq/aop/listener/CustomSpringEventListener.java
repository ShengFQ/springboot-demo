package io.shengfq.aop.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import io.shengfq.aop.event.custom.CustomSpringEvent;


/**
 * 事件监听器 1.验证多个监听器监听同一个事件的执行顺序,通过@Order来调整监听器调用顺序
 *
 * @author shengfq
 */

@Component
public class CustomSpringEventListener {
  private static final Logger log = LoggerFactory.getLogger(CustomSpringEventListener.class);

  @Async
  @Order(2)
  @EventListener
  public void onApplicationEvent1(CustomSpringEvent event) {
    log.info("接收到自定义事件1 - " + event.getMessage());
  }

  @Async
  @Order(1)
  @EventListener
  public void onApplicationEvent2(CustomSpringEvent event) {
    log.info("接收到自定义事件2- {}", event.getMessage());
  }
}
