package com.shengfq.pss;

import com.shengfq.pss.config.MybatisPlusConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@ImportAutoConfiguration(classes = {MybatisPlusConfig.class})
@SpringBootApplication
public class PhotoSpringBootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhotoSpringBootDemoApplication.class, args);
    }

}
