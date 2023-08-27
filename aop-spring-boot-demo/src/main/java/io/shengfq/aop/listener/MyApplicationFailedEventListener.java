package io.shengfq.aop.listener;

import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.context.event.EventListener;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: MyApplicationFailedEventListener Description: TODO
 *
 * @author shengfq
 * @date: 2023/8/27 5:39 下午
 */
@Slf4j
public class MyApplicationFailedEventListener {
  @EventListener
  public void applicationEvent1(ApplicationFailedEvent event) {
    log.info("接收到spring内置事件1 - {}", event.getException());
  }
}
