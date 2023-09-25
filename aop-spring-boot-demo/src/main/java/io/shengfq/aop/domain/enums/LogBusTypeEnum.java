package io.shengfq.aop.domain.enums;

import lombok.Getter;

/**
 * 业务类型
 */
@Getter
public enum LogBusTypeEnum {
  IN("IN", "IN"), OUT("IN", "IN");

  private String code;
  private String value;

  LogBusTypeEnum(String code, String value) {
    this.code = code;
    this.value = value;
  }
}
