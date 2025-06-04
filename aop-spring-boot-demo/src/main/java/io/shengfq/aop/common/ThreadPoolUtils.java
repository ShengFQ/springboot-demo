package io.shengfq.aop.common;

import java.util.concurrent.CompletableFuture;

import javax.annotation.Resource;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import io.shengfq.aop.domain.LoginVal;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: ThreadPoolUtils Description: 线程池工具类
 *
 * @author shengfq
 * @date: 2025/5/31 16:39
 */
@Slf4j
@Component
public class ThreadPoolUtils {

  @Resource(name = "taskExecutor")
  private ThreadPoolTaskExecutor taskExecutor;

  /**
   * 手动设置父子线程的上下文对象传递 不推荐
   */
  public void handlerAsyncBy() {
    // 1. 获取父线程的loginVal
    LoginVal loginVal = OauthContext.get();
    log.info("父线程的值：{}", OauthContext.get());
    CompletableFuture.runAsync(() -> {
      // 2. 设置子线程的值，复用
      OauthContext.set(loginVal);
      log.info("子线程的值：{}", OauthContext.get());
    });
  }

  /**
   * 通过自定义的线程池和装饰器注入实现 无需手动传递上下文
   */
  public void handlerAsync(Runnable runnable) {
    log.info("父线程的LoginVal：{}", OauthContext.get());
    // 执行异步任务，需要指定的线程池
    // () -> log.info("子线程的用户信息：{}", OauthContext.get())
    CompletableFuture.runAsync(runnable, taskExecutor);
  }

}
