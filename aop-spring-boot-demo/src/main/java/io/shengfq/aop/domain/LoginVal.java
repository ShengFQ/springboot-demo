package io.shengfq.aop.domain;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

/**
 * ClassName: LoginVal Description: 会话对象
 *
 * @author shengfq
 * @date: 2025/6/4 22:09
 */
@Data
@Builder
@ToString
public class LoginVal {

  private String userName;

  private String facilityCode;
}
