package com.mybatis.spring.demo.pool;
/**
 * 异步任务
 * */
@FunctionalInterface
public interface ITask {
    public void executeTask();
}
