package com.shengfq.toolkit.pojo;

import com.shengfq.toolkit.common.BizException;

import lombok.Data;

/**
 * ClassName: RespResultVo Description: 接口响应对象
 *
 * @author shengfq
 * @date: 2025/5/11 12:32
 */
@Data
public class RespResultVo<T> {
  private boolean success;

  private T data;

  private String message;

  public static <T> RespResultVo success(T data) {
    RespResultVo result = new RespResultVo();
    result.setSuccess(true);
    result.setData(data);
    result.message = "success";
    return result;
  }

  public static <T> RespResultVo fail(String message) {
    RespResultVo result = new RespResultVo();
    result.setSuccess(false);
    result.setData(null);
    result.message = message;
    return result;
  }

  public static <T> RespResultVo fail(BizException exception) {
    RespResultVo result = new RespResultVo();
    result.setSuccess(false);
    result.setData(exception.getErrorCode());
    result.message = exception.getErrorMsg();
    return result;
  }
}
