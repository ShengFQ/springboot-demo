package io.shengfq.aop.publish;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import cn.hutool.core.exceptions.ExceptionUtil;
import io.shengfq.aop.event.custom.CustomSpringEvent;


/**
 * 自定义事件的,事件发布器
 *
 * @author sheng
 */

@Component
public class CustomSpringEventPublisher {
  private static final Logger log = LoggerFactory.getLogger(CustomSpringEventPublisher.class);

  @Autowired
  private ApplicationEventPublisher applicationEventPublisher;

  public void publishCustomEvent(final String message) {
    log.info("发布 CustomSpringEvent 事件 消息{}. ", message);
    CustomSpringEvent customSpringEvent = new CustomSpringEvent(this, message);
    try {
      applicationEventPublisher.publishEvent(customSpringEvent);
      Thread.sleep(3000);
      log.info("生产者业务执行完成.");
    } catch (Exception ex) {
      log.error(ExceptionUtil.stacktraceToString(ex));
    }

  }

}
