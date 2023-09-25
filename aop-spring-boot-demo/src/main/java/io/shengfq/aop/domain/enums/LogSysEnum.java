package io.shengfq.aop.domain.enums;

import lombok.Getter;

/**
 * 关联系统
 */
@Getter
public enum LogSysEnum {
  PC("PC", "PC"), APP("APP", "APP");

  private String code;
  private String value;

  LogSysEnum(String code, String value) {
    this.code = code;
    this.value = value;
  }
}
