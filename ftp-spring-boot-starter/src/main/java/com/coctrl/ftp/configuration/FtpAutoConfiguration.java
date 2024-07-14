package com.coctrl.ftp.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.coctrl.ftp.service.FtpClientFactory;
import com.coctrl.ftp.service.FtpClientTemplate;

@Configuration
@EnableConfigurationProperties(FtpClientProperties.class)
@ConditionalOnClass(FtpClientTemplate.class)
@ConditionalOnProperty(prefix = "ftp.client", value = "enabled", matchIfMissing = true)
public class FtpAutoConfiguration {
  private final FtpClientProperties properties;

  public FtpAutoConfiguration(FtpClientProperties properties) {
    this.properties = properties;
  }

  @Bean
  @ConditionalOnMissingBean
  public FtpClientFactory ftpClientFactory() {
    return new FtpClientFactory(properties);
  }

  @Bean
  @ConditionalOnMissingBean
  public FtpClientTemplate ftpClientTemplate(FtpClientFactory ftpClientFactory) {
    return new FtpClientTemplate(ftpClientFactory);
  }
}
