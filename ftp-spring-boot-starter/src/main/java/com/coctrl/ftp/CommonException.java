package com.coctrl.ftp;

/**
 * ClassName: CommonException Description: 业务异常
 *
 * @author shengfq
 * @date: 2023/10/21 10:08 上午
 */
public class CommonException extends RuntimeException {
  public CommonException() {
    super();
  }

  public CommonException(String msg) {
    super(msg);
  }

  public CommonException(Exception e) {
    super(e);
  }
}
