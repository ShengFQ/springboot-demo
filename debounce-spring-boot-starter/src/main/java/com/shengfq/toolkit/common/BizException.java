package com.shengfq.toolkit.common;

import lombok.Getter;
import lombok.Setter;

/**
 * ClassName: BizException Description: 业务异常类
 *
 * @author shengfq
 * @date: 2025/4/20 22:14
 */
@Getter
@Setter
public class BizException extends RuntimeException {
  /**
   * 错误码
   */
  private ResponseCodeEnum errorCode;

  /**
   * 自定义错误信息
   */
  private String errorMsg;

  public BizException(ResponseCodeEnum errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
    this.errorMsg = message;
  }
}
