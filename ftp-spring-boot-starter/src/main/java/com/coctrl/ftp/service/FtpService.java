package com.coctrl.ftp.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.coctrl.ftp.configuration.FtpProperties;


/**
 * 类 FtpService 功能描述：
 *
 * @author kangaroo hy
 * @version 0.0.1
 * @date 2020/11/10
 */
@Service
public class FtpService {
  public static final Logger logger = Logger.getLogger(FtpService.class);

  private final FtpProperties properties;

  private Queue<FTPClient> queue = new ConcurrentLinkedQueue<FTPClient>();

  public FtpService(FtpProperties properties) {
    this.properties = properties;
  }

  public boolean uploadFile(String fileName, InputStream inputStream) {
    FTPClient ftpClient = connect();
    if (ftpClient != null) {
      try {
        boolean b = ftpClient.storeFile(fileName, inputStream);
        if (b) {
          logger.info("上传成功");
        } else {
          logger.info("上传失败");
        }
        inputStream.close();
        return b;
      } catch (IOException e) {
        logger.info("上传失败：" + e.getMessage());
      } finally {
        disconnect(ftpClient);
      }
    }
    return false;
  }

  private FTPClient connect() {
    FTPClient client = queue.poll();
    if (client != null) {
      return client;
    }
    FTPClient ftpClient = new FTPClient();
    ftpClient.setControlEncoding("utf-8");
    try {
      ftpClient.connect(properties.getHost(), properties.getPort());
      if (StringUtils.isEmpty(properties.getUsername())
          || StringUtils.isEmpty(properties.getPassword())) {
        ftpClient.login("anonymous", null);
      } else {
        ftpClient.login(properties.getUsername(), properties.getPassword());
      }
      int replyCode = ftpClient.getReplyCode();
      if (!FTPReply.isPositiveCompletion(replyCode)) {
        logger.error("连接服务器失败");
        throw new IOException("不能连接到FTP服务器：" + properties.getHost());
      }
      logger.info("连接服务器成功");
      ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
      ftpClient.enterLocalPassiveMode();
      boolean b = ftpClient.makeDirectory(properties.getPath());
      if (b) {
        logger.info("路径创建成功");
      } else {
        logger.info("路径已存在，直接使用");
      }
      ftpClient.changeWorkingDirectory(properties.getPath());
      queue.add(ftpClient);
      return ftpClient;
    } catch (IOException e) {
      logger.error("连接服务器成功", e);
      return null;
    }
  }

  void disconnect(FTPClient client) {
    queue.add(client);
  }
}
