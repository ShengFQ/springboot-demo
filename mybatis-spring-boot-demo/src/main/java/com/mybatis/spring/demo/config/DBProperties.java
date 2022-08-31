package com.mybatis.spring.demo.config;

import com.zaxxer.hikari.HikariConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari")
public class DBProperties extends HikariConfig {
}
