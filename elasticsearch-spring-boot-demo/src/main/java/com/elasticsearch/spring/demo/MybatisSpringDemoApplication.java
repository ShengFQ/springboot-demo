package com.elasticsearch.spring.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class MybatisSpringDemoApplication{
    /**
     * 默认线程池
     */


    public static void main(String[] args) {
        SpringApplication.run(MybatisSpringDemoApplication.class, args);
    }
}
