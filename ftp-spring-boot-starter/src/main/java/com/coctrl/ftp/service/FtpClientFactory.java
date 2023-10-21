package com.coctrl.ftp.service;

import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.net.util.TrustManagerUtils;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.coctrl.ftp.configuration.FtpClientProperties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(prefix = "ftp.client", value = "enabled", matchIfMissing = true)
public class FtpClientFactory extends BasePooledObjectFactory<FTPSClient> {

  private FtpClientProperties config;

  @Autowired
  public FtpClientFactory(FtpClientProperties config) {
    this.config = config;
  }

  /**
   * 创建FtpClient对象
   */
  @Override
  public FTPSClient create() {
    FTPSClient ftpClient = new FTPSClient();
    ftpClient.setControlEncoding(config.getEncoding());
    // ftpClient.setDataTimeout(config.getDataTimeout());
    // ftpClient.setConnectTimeout(config.getConnectTimeout());
    // ftpClient.setControlKeepAliveTimeout(config.getKeepAliveTimeout());
    ftpClient.setTrustManager(TrustManagerUtils.getAcceptAllTrustManager());// 必须设置此属性,通过TSL加密连接
    try {
      ftpClient.connect(config.getHost(), config.getPort());
      int replyCode = ftpClient.getReplyCode();
      if (!FTPReply.isPositiveCompletion(replyCode)) {
        ftpClient.disconnect();
        log.warn("ftpServer refused connection,replyCode:{}", replyCode);
        return null;
      }

      if (!ftpClient.login(config.getUsername(), config.getPassword())) {
        log.warn("ftpClient login failed... username is {}; password: {}", config.getUsername(),
            config.getPassword());
      }
      ftpClient.changeWorkingDirectory(config.getPath());
      ftpClient.setBufferSize(config.getBufferSize());
      ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
      if (config.isPassiveMode()) {
        ftpClient.enterLocalPassiveMode();
      }
      ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
      ftpClient.execPROT("P");
    } catch (IOException e) {
      log.error("create ftp connection failed...", e);
    }
    return ftpClient;
  }

  /**
   * 用PooledObject封装对象放入池中
   */
  @Override
  public PooledObject<FTPSClient> wrap(FTPSClient ftpClient) {
    return new DefaultPooledObject<>(ftpClient);
  }

  /**
   * 销毁FtpClient对象
   */
  @Override
  public void destroyObject(PooledObject<FTPSClient> ftpPooled) {
    if (ftpPooled == null) {
      return;
    }
    FTPClient ftpClient = ftpPooled.getObject();
    try {
      ftpClient.logout();
    } catch (IOException io) {
      log.error("ftp client logout failed...{}", io);
    } finally {
      try {
        if (ftpClient.isConnected()) {
          ftpClient.disconnect();
        }
      } catch (IOException io) {
        log.error("close ftp client failed...{}", io);
      }
    }
  }

  /**
   * 验证FtpClient对象
   */
  @Override
  public boolean validateObject(PooledObject<FTPSClient> ftpPooled) {
    try {
      FTPClient ftpClient = ftpPooled.getObject();
      if (!ftpClient.sendNoOp()) {
        return false;
      }
      // 验证FTP服务器是否登录成功
      int replyCode = ftpClient.getReplyCode();
      if (!FTPReply.isPositiveCompletion(replyCode)) {
        log.warn("ftpServer refused connection, replyCode:{}", replyCode);
        return false;
      }
      return true;
    } catch (IOException e) {
      log.error("failed to validate client: {}", e);
    }
    return false;
  }
}
