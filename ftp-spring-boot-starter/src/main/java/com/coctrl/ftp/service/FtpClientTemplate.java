package com.coctrl.ftp.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import javax.annotation.PreDestroy;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.util.ObjectUtils;

import com.coctrl.ftp.CommonException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FtpClientTemplate {
  public final String ROOT_PATH = File.separator + "domestic2" + File.separator + "mom";
  private final GenericObjectPool<FTPSClient> ftpClientPool;

  private FtpClientFactory ftpClientFactory;

  public FtpClientTemplate(FtpClientFactory ftpClientFactory) {
    this.ftpClientPool = new GenericObjectPool<>(ftpClientFactory);
    this.ftpClientPool.setTestOnBorrow(true);
    this.ftpClientFactory = ftpClientFactory;
  }

  @PreDestroy
  public void destroy() {
    this.ftpClientPool.close();
  }

  /***
   * 上传Ftp文件
   *
   * @param localFile 当地文件内容
   * @param remotePath 上传服务器路径 - 应该以/结束
   * @return true or false
   */
  public boolean uploadFile(byte[] localFile, String remotePath, String fileName) {
    if (localFile == null) {
      log.error("上传文件字节为空");
      return false;
    }
    FTPSClient ftpClient = null;
    BufferedInputStream inStream = null;
    boolean upload = false;
    try {
      // 从池中获取对象
      ftpClient = ftpClientFactory.create();
      // 验证FTP服务器是否登录成功
      int replyCode = ftpClient.getReplyCode();
      if (!FTPReply.isPositiveCompletion(replyCode)) {
        log.warn("ftpServer refused connection, replyCode:{}", replyCode);
        throw new CommonException("登录FTP服务器失败");
      }
      // 改变工作路径
      // ftpClient.changeWorkingDirectory(remotePath);
      boolean makeDirectory = ftpClient.makeDirectory(remotePath);
      if (makeDirectory) {
        log.info("路径创建成功 {}", remotePath);
      } else {
        log.info("路径已存在，直接使用 {}", remotePath);
      }
      ftpClient.changeWorkingDirectory(remotePath);
      inStream = new BufferedInputStream(new ByteArrayInputStream(localFile));
      log.info("start upload... {}", fileName);
      upload = ftpClient.storeFile(fileName, inStream);
      log.info("upload file success! {}", fileName);
    } catch (Exception e) {
      log.error("upload file failure!", e);
      throw new CommonException("上传文件失败");
    } finally {
      if (inStream != null) {
        try {
          inStream.close();
        } catch (IOException e) {
          log.error("输入流关闭失败", e);
        }
      }
      // 将对象放回池中
      destroy(ftpClient);
    }
    return upload;
  }

  /**
   * 下载文件
   *
   * @param remotePath FTP服务器文件目录
   * @param fileName 需要下载的文件名称
   * @return true or false
   */
  public byte[] downloadFile(String remotePath, String fileName) {
    FTPSClient ftpClient = null;
    byte[] fileByte = null;
    try {
      ftpClient = ftpClientFactory.create();
      // 切换FTP目录
      FTPFile[] ftpFiles = ftpClient.listFiles(remotePath);
      for (FTPFile file : ftpFiles) {
        if (fileName.equals(file.getName())) {
          /*
           * fileByte = IOUtils.toByteArray(ftpClient.retrieveFileStream(remotePath + "/" +
           * file.getName()));
           */
          break;
        }
      }
      if (ObjectUtils.isEmpty(fileByte)) {
        throw new CommonException("未找到对应的文件,请联系管理员");
      }
      return fileByte;
    } catch (Exception e) {
      log.error("download file failure!", e);
      throw new CommonException(e);
    } finally {
      destroy(ftpClient);
    }
  }

  /**
   * 获取文件大小
   *
   * @param remotePath FTP服务器文件目录
   * @param fileName 需要下载的文件名称
   * @return true or false
   */
  public Long getFileSize(String remotePath, String fileName) {
    FTPSClient ftpClient = null;
    try {
      ftpClient = ftpClientFactory.create();
      // 切换FTP目录
      FTPFile[] ftpFiles = ftpClient.listFiles(remotePath);
      for (FTPFile file : ftpFiles) {
        if (fileName.equals(file.getName())) {
          return file.getSize();
        }
      }
      return 0L;
    } catch (Exception e) {
      log.error("download file failure!", e);
      throw new CommonException(e);
    } finally {
      destroy(ftpClient);
    }
  }

  private void destroy(FTPClient ftpClient) {
    if (ftpClient == null) {
      return;
    }
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

  public String getRemotePath(String remotePath) {
    remotePath = ROOT_PATH + File.separator + remotePath;
    return remotePath;
  }

}
