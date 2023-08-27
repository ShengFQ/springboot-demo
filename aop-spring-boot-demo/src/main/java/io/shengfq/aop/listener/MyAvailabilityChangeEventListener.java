package io.shengfq.aop.listener;

import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 监听 AvailabilityChangeEvent事件
 *
 * @author sheng
 */
@Slf4j
@Component
public class MyAvailabilityChangeEventListener
    implements ApplicationListener<AvailabilityChangeEvent> {

  @Override
  public void onApplicationEvent(AvailabilityChangeEvent event) {
    log.info("监听到事件：{}", event);
    if (ReadinessState.ACCEPTING_TRAFFIC == event.getState()) {
      log.info("应用启动完成，可以请求了……");
    }
  }

}
