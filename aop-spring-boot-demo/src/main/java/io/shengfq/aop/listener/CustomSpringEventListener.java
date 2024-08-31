package io.shengfq.aop.listener;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import io.shengfq.aop.domain.vo.InterfaceLogVo;
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

  /**
   * 同步事件,抛出异常影响到生产者
   */
  @Order(1)
  @EventListener
  public void onApplicationEvent1(CustomSpringEvent event) {
    log.info("接收到自定义事件1 - 同步线程" + event.getMessage());
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    InterfaceLogVo interfaceLogVo = new InterfaceLogVo();
    interfaceLogVo.setRequestMsg(event.getMessage());
  }

  /**
   * 同步事件,使用新的事务进行提交
   */
  @Order(3)
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void onApplicationEvent3(CustomSpringEvent event) {
    log.info("接收到自定义事件3- 同步-新事务-{}", event.getMessage());
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 异步事件,抛出异常不会影响到生产者
   */
  @Async
  @Order(2)
  @EventListener
  public void onApplicationEvent2(CustomSpringEvent event) {
    log.info("接收到自定义事件2- 异步-新事务-{}", event.getMessage());
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Async
  @Order(4)
  @EventListener
  public void onApplicationEvent4(CustomSpringEvent event) {
    log.info("接收到自定义事件4- 异步-新事务-{}", event.getMessage());
    try {
      Thread.sleep(3000);
      throw new RuntimeException("hahahahah");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Async
  @Order(5)
  @EventListener
  public void onApplicationEvent5(CustomSpringEvent event) {
    log.info("接收到自定义事件5- 异步-新事务-{}", event.getMessage());
    try {
      Thread.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * 声明一个异步线程方法,但是预测结果并非在异步线程中执行 因为其调用方是个同步方法
   */
  @Async
  public void onApplicationEvent6(String message) {
    log.info("异步线程方法执行 {}-{}", Thread.currentThread().getName(), message);
  }
}
