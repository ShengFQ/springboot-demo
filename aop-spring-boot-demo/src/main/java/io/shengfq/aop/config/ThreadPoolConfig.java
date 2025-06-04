package io.shengfq.aop.config;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import io.shengfq.aop.common.ContextTaskDecorator;


/**
 * ClassName: ThreadPoolConfig Description: 线程池初始化
 *
 * @author shengfq
 * @date: 2025/5/31 16:49
 */
@Configuration
public class ThreadPoolConfig {
  @Bean("ThreadPool01")
  public ThreadPoolExecutor initThreadPoolExecutor() {
    return new ThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
  }

  @Bean("taskExecutor")
  public ThreadPoolTaskExecutor taskExecutor() {
    ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
    poolTaskExecutor.setCorePoolSize(2);
    poolTaskExecutor.setMaxPoolSize(4);
    // 设置线程活跃时间（秒）
    poolTaskExecutor.setKeepAliveSeconds(120);
    // 设置队列容量
    poolTaskExecutor.setQueueCapacity(100);
    // 设置TaskDecorator，用于解决父子线程间的数据复用
    poolTaskExecutor.setTaskDecorator(new ContextTaskDecorator());
    poolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    // 等待所有任务结束后再关闭线程池
    poolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
    return poolTaskExecutor;
  }
}
