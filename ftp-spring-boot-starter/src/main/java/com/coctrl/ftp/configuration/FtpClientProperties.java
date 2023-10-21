package com.coctrl.ftp.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "ftp.client")
public class FtpClientProperties {

  /**
   * 服务器地址，默认 localhost
   */
  private String host = "localhost";

  /**
   * 通信端口，默认 21
   */
  private Integer port = 21;

  /**
   * 用户名
   */
  private String username = "";

  /**
   * 密码
   */
  private String password = "";

  /**
   * 文件上传路径，支持一级，可以不用 / 如：test，不用：/test
   */
  private String path = "/domestic2/mom";
  /**
   * 编码
   */
  private String encoding = "UTF-8";

  private int bufferSize = 1024 * 1024 * 20;

  private boolean isPassiveMode = true;
}
