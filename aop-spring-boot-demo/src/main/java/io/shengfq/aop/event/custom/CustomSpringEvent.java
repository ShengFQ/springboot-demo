package io.shengfq.aop.event.custom;

import org.springframework.context.ApplicationEvent;

/**
 * ClassName: CustomSpringEvent Description: 事件对象-传递参数
 *
 * @author shengfq
 * @date: 2023/8/24 9:59 下午
 */
public class CustomSpringEvent extends ApplicationEvent {
  private String message;

  public CustomSpringEvent(Object source, String message) {
    super(source);
    this.message = message;
  }

  public CustomSpringEvent(Object source) {
    super(source);
  }

  public String getMessage() {
    return message;
  }
}
