package com.shengfq.toolkit.common;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.shengfq.toolkit.pojo.RespResultVo;

/**
 * ClassName: GlobalExceptionHandler Description: 全局异常拦截处理
 *
 * @author shengfq
 * @date: 2025/5/11 10:26
 */
@RestControllerAdvice(
    basePackages = {"com.shengfq.toolkit.controller", "com.shengfq.toolkit.debounce"})
public class GlobalExceptionHandler {
  @ExceptionHandler(BizException.class)
  public RespResultVo handler(BizException ex, HttpServletResponse response) {
    return RespResultVo.fail(ex);
  }

}
