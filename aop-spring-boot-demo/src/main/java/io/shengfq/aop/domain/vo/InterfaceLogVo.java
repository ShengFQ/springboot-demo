package io.shengfq.aop.domain.vo;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClassName: InterfaceLogVo Description: 接口
 *
 * @author shengfq
 * @date: 2023/9/25 9:24 下午
 */
@Data
@Builder
@NoArgsConstructor
public class InterfaceLogVo {
  private String logType;

  private String type;

  private String sys;

  private String startDate;

  private String trackingNo;

  private String requestMsg;

  private String responseMsg;

  private String status;

  private String endDate;

  private String key;

  private String factory;

  private String factoryName;
}
