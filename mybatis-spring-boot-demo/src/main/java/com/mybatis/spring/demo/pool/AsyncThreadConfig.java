package com.mybatis.spring.demo.pool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 重载 @Async的线程池实现
 * 2.要配置默认的线程池，要实现AsyncConfigurer类的两个方法
 * 不需要打印运行状况的可以使用ThreadPoolTaskExecutor类构建线程池
 * */
@Slf4j
@EnableAsync
@Configuration
public class AsyncThreadConfig implements AsyncConfigurer {
    /**
     * 定义@Async默认的线程池
     * ThreadPoolTaskExecutor不是完全被IOC容器管理的bean,可以在方法上加上@Bean注解交给容器管理,这样可以将taskExecutor.initialize()方法调用去掉，容器会自动调用
     *
     * @return
     */
    @Bean("service2Executor")
    @Override
    public Executor getAsyncExecutor() {
        log.info("自定义实例化ThreadPoolTaskExecutor...");
        int processors = Runtime.getRuntime().availableProcessors();
        //常用的执行器
        //ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //可以查看线程池参数的自定义执行器
        ThreadPoolTaskExecutor taskExecutor = new VisibleThreadPoolTaskExecutor();
        //核心线程数
        taskExecutor.setCorePoolSize(1);
        taskExecutor.setMaxPoolSize(2);
        //线程队列最大线程数,默认：50
        taskExecutor.setQueueCapacity(50);
        //线程名称前缀
        taskExecutor.setThreadNamePrefix("customer-task-pool");
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化(重要)
        taskExecutor.initialize();
        return taskExecutor;
     }

    /**
     * 异步方法执行的过程中抛出的异常捕获
     *
     * @return
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (ex, method, params) ->
                log.error("线程池执行任务发送未知错误,执行方法：{}", method.getName(), ex.getMessage());
    }

}
