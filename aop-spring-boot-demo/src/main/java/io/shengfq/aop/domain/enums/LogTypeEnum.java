package io.shengfq.aop.domain.enums;

import lombok.Getter;

/**
 * 日志类型
 */
@Getter
public enum LogTypeEnum {
  HELLO("HELLO", "IN"), INDEX("INDEX", "IN");

  private String code;
  private String value;

  LogTypeEnum(String code, String value) {
    this.code = code;
    this.value = value;
  }
}
