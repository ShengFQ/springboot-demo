package io.shengfq.aop.listener;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import io.shengfq.aop.event.custom.CustomSpringEvent;
import lombok.extern.slf4j.Slf4j;


/**
 * 事件监听器 1.验证多个监听器监听同一个事件的执行顺序,通过@Order来调整监听器调用顺序
 *
 * @author shengfq
 */
@Slf4j
@Component
public class CustomSpringEventListener {
  // private static final Logger log = LoggerFactory.getLogger(CustomSpringEventListener.class);

  /**
   * 同步事件,抛出异常影响到生产者
   */
  @Order(1)
  @EventListener
  public void onApplicationEvent1(CustomSpringEvent event) {
    log.info("接收到自定义事件1 - " + event.getMessage());
    // throw new RuntimeException("消费者1抛出异常");
  }

  /**
   * 同步事件,使用新的事务进行提交
   */
  @Order(3)
  @TransactionalEventListener
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void onApplicationEvent3(CustomSpringEvent event) {
    log.info("接收到自定义事件3- {}", event.getMessage());
    throw new RuntimeException("消费者3抛出异常");
  }

  /**
   * 异步事件,抛出异常不会影响到生产者
   */
  @Async
  @Order(2)
  @EventListener
  public void onApplicationEvent2(CustomSpringEvent event) {
    log.info("接收到自定义事件2- {}", event.getMessage());
    throw new RuntimeException("消费者2抛出异常");
  }
}
