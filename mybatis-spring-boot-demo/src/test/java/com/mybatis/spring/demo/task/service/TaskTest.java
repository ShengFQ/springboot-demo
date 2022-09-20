package com.mybatis.spring.demo.task.service;

import com.mybatis.spring.demo.pool.ExecuteTaskUtils;
import com.mybatis.spring.demo.pool.ICallback;
import com.mybatis.spring.demo.pool.ITask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * ClassName: TaskTest
 * Description: TODO
 *
 * @author shengfq
 * @date: 2022/8/24 11:08 上午
 */
@Slf4j
@SpringBootTest
public class TaskTest {
    //匿名内部类实例变量初始化
    private ITask task1=new ITask() {
        @Override
        public void executeTask() {
            System.out.println("task1 start");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task1 end");
        }
    };
    //Lambda表达式初始化
    private ITask task2=()->{
        System.out.println("task2 start");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("task2 end");
    };

    private ICallback callback1=new ICallback() {
        @Override
        public void callback(boolean isSuccess) {
            System.out.println("callback1 start");
            if(isSuccess){
                System.out.println("task is success");
            }else{
                System.out.println("task is fail");
            }
        }
    };

    @Test
    public void testTask(){
        ExecuteTaskUtils.submitTask(task1);
        System.out.println("main thread is over");
    }

    @Test
    public void testCallback() throws InterruptedException {
        ExecuteTaskUtils.submitTask(task2,callback1);
        Thread.sleep(10*1000);
        System.out.println("main thread is over");
    }

    @Async
    public void defaultThread() throws Exception {
        long start = System.currentTimeMillis();
        Random random=new Random();
        Thread.sleep(random.nextInt(1000));
        long end = System.currentTimeMillis();
        log.info("使用默认线程池，耗时：" + (end - start) + "毫秒");
    }
    @Test
    public void testDefaultThreadPool() throws Exception {
        defaultThread();
        System.out.println("main thread is over");
    }
    /**
     * 如果有返回值，返回类型应该为 Future<>
     * @return
     */
    @Async("service2Executor")
    public Future<String> getResult() throws InterruptedException {
        Thread.sleep(30 * 1000);
        return new AsyncResult<>("haha");
    }
    @Test
    public void testGetResultThreadPool() throws InterruptedException, ExecutionException, TimeoutException {
        Future<String> future=getResult();
        String result=future.get(4000, TimeUnit.MILLISECONDS);
        System.out.println("main thread is over ,result:"+result);
    }

    @Async("service2Executor")
    public void service2Executor() throws Exception {
        long start = System.currentTimeMillis();
        Random random=new Random();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("使用线程池service2Executor，耗时：" + (end - start) + "毫秒");
    }
    /**
     * TODO @@Checked
     * 基于@Async
     * 使用自定义线程池执行异步任务,并没有异步执行线程池任务.
     * */
    @Test
    public void testMyThreadPool() throws Exception {
        service2Executor();
        System.out.println("main thread is over");
    }
}
