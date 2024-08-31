package com.shengfq.eureka.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringEurekaClientProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEurekaClientProviderApplication.class, args);
    }

}
