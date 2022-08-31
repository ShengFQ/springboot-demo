package com.mybatis.spring.demo;

import com.mybatis.spring.demo.config.CmsDbConfig;
import com.mybatis.spring.demo.config.DBProperties;
import com.mybatis.spring.demo.config.OpenDbConfig;
import com.mybatis.spring.demo.config.SchoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@ImportAutoConfiguration(classes = {DBProperties.class, CmsDbConfig.class, OpenDbConfig.class, SchoolConfig.class})
@SpringBootApplication
public class MybatisSpringDemoApplication{
    /**
     * 默认线程池
     */


    public static void main(String[] args) {
        SpringApplication.run(MybatisSpringDemoApplication.class, args);
    }
}
