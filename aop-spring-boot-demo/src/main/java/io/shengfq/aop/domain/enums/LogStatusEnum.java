package io.shengfq.aop.domain.enums;

import lombok.Getter;

@Getter
public enum LogStatusEnum {
  READY("IN", "IN"), FAIL("IN", "IN"), FINISH("IN", "IN");

  private String code;
  private String value;

  LogStatusEnum(String code, String value) {
    this.code = code;
    this.value = value;
  }
}
