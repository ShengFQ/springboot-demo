package io.shengfq.aop.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.extern.slf4j.Slf4j;

/**
 * 线程池初始化方法1 实现AsyncConfigurer接口
 * Executor.class:ThreadPoolExecutorAdapter->ThreadPoolExecutor->AbstractExecutorService->ExecutorService->Executor（这样的模式，最终底层为Executor.class，在替换默认的线程池时，需设置默认的线程池名称为TaskExecutor）
 *
 * TaskExecutor.class:ThreadPoolTaskExecutor->SchedulingTaskExecutor->AsyncTaskExecutor->TaskExecutor（这样的模式，最终底层为TaskExecutor.class，在替换默认的线程池时，可不指定线程池名称。）\
 *
 * 平常用没注意@Async异步方法，默认使用的是SimpleAsyncTaskExecutor
 */
@Slf4j
@Configuration
public class AsyncConfiguration implements AsyncConfigurer {

  @Bean("kingAsyncExecutor")
  public ThreadPoolTaskExecutor executor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    int corePoolSize = 10;
    executor.setCorePoolSize(corePoolSize);
    int maxPoolSize = 50;
    executor.setMaxPoolSize(maxPoolSize);
    int queueCapacity = 10;
    executor.setQueueCapacity(queueCapacity);
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    String threadNamePrefix = "kingDeeAsyncExecutor-";
    executor.setThreadNamePrefix(threadNamePrefix);
    executor.setWaitForTasksToCompleteOnShutdown(true);
    // 使用自定义的跨线程的请求级别线程工厂类
    int awaitTerminationSeconds = 5;
    executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
    executor.initialize();
    return executor;
  }

  @Override
  public Executor getAsyncExecutor() {
    return executor();
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return (ex, method, params) -> log.info(String.format("执行异步任务'%s'", method), ex.getMessage());
  }
}
