package io.shengfq.aop.config;

import java.util.concurrent.Executor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程池初始化方法2 继承AsyncConfigurerSupport
 */
@Slf4j
// @Configuration
class SpringAsyncConfigurer extends AsyncConfigurerSupport {

  // @Bean
  public ThreadPoolTaskExecutor asyncExecutor() {
    ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
    threadPool.setCorePoolSize(3);
    threadPool.setMaxPoolSize(3);
    threadPool.setWaitForTasksToCompleteOnShutdown(true);
    threadPool.setAwaitTerminationSeconds(60 * 15);
    return threadPool;
  }

  @Override
  public Executor getAsyncExecutor() {
    return asyncExecutor();
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return (ex, method, params) -> log.info(String.format("执行异步任务'%s'", method), ex);
  }
}
