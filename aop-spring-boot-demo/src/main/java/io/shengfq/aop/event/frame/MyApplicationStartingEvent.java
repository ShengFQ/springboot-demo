package io.shengfq.aop.event.frame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationStartingEvent;

/**
 * ClassName: ApplicationStartingEvent Description: 这个事件在 Spring Boot
 * 应用运行开始时，且进行任何处理之前发送（除了监听器和初始化器注册之外）。
 *
 * @author shengfq
 * @date: 2023/8/27 10:17 上午
 */
public class MyApplicationStartingEvent extends ApplicationStartingEvent {
  public MyApplicationStartingEvent(SpringApplication application, String[] args) {
    super(application, args);
  }
}
