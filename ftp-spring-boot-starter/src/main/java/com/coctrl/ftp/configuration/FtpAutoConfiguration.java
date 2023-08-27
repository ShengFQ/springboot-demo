package com.coctrl.ftp.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.coctrl.ftp.service.FtpService;

/**
 * @author kangaroo hy
 * @date 2020/4/25
 * @desc ftp-spring-boot-starter
 * @since 0.0.1
 */
@Configuration
@EnableConfigurationProperties(FtpProperties.class)
@ConditionalOnClass(FtpService.class)
@ConditionalOnProperty(prefix = "coctrl.ftp", value = "enabled", matchIfMissing = true)
public class FtpAutoConfiguration {
  private final FtpProperties properties;

  public FtpAutoConfiguration(FtpProperties properties) {
    this.properties = properties;
  }

  @Bean
  @ConditionalOnMissingBean(FtpService.class)
  public FtpService ftpService() {
    return new FtpService(properties);
  }
}
