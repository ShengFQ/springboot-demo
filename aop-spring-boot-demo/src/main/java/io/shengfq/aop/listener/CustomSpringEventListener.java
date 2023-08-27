package io.shengfq.aop.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import io.shengfq.aop.event.custom.CustomSpringEvent;


/**
 * 事件监听器
 *
 * @author shengfq
 */

@Component
public class CustomSpringEventListener implements ApplicationListener<CustomSpringEvent> {
  private static final Logger log = LoggerFactory.getLogger(CustomSpringEventListener.class);

  @Override
  public void onApplicationEvent(CustomSpringEvent event) {
    log.info("接收到自定义事件 - " + event.getMessage());
  }
}
