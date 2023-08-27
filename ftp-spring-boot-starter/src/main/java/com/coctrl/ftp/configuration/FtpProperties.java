package com.coctrl.ftp.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 类 FtpProperties 功能描述：
 *
 * @author kangaroo hy
 * @version 0.0.1
 * @date 2020/11/10
 */
@Component
@ConfigurationProperties(prefix = "coctrl.ftp")
public class FtpProperties {

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
  private String path = "";

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public Integer getPort() {
    return port;
  }

  public void setPort(Integer port) {
    this.port = port;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }
}


