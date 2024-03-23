package com.shengfq.webserver;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: IndexController
 * Description: 验证内置的webserver的配置参数
 * springboot-内置tomcat连接参数表
 * 最大连接数 server.tomcat.max-connections 8192
 * 最大等待数 server.tomcat.accept-count 100
 * 最少线程数 server.tomcat.threads.min-spare 10
 * 最多线程数 server.tomcat.threads.max 200
 *
 * @author shengfq
 * @date: 2024-03-23
 */
@RestController
public class IndexController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/shutdown")
    public void shutdown() {
        System.exit(9);
    }

    @GetMapping("/wait")
    public String waits() {
        try {
            System.out.println("current thread start:"+Thread.currentThread().getName());
            Thread.sleep(5*1000);
            System.out.println("current thread end:"+Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Thread.currentThread().getName();
    }

}
