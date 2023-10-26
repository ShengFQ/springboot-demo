package io.shengfq.aop.config;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.aop.interceptor.AsyncExecutionAspectSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 线程池的初始化实现方式3 配置由自定义的TaskExecutor替代内置的任务执行器
 */
@Configuration
public class TaskPoolConfig {
  @Bean(name = AsyncExecutionAspectSupport.DEFAULT_TASK_EXECUTOR_BEAN_NAME)
  public Executor taskExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    // 核心线程池大小
    executor.setCorePoolSize(10);
    // 最大线程数
    executor.setMaxPoolSize(20);
    // 队列容量
    executor.setQueueCapacity(200);
    // 活跃时间
    executor.setKeepAliveSeconds(60);
    // 线程名字前缀
    executor.setThreadNamePrefix("taskExecutor-");
    executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    return executor;
  }
}
