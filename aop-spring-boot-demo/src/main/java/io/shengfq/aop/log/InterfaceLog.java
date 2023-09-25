package io.shengfq.aop.log;

import java.lang.annotation.*;

import io.shengfq.aop.domain.enums.LogBusTypeEnum;
import io.shengfq.aop.domain.enums.LogSysEnum;
import io.shengfq.aop.domain.enums.LogTypeEnum;

/**
 * @description 接口日志记录注解(适用于接收上游mq消息这种场景)
 * @author guoy59
 * @date 2022-10-09
 * @version 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface InterfaceLog {
  /**
   * 日志类型
   *
   * @return
   */
  LogTypeEnum type();


  /**
   * 业务类型
   *
   * @return
   */
  LogBusTypeEnum busType();

  /**
   * 关联系统
   *
   * @return
   */
  LogSysEnum logSys();

  /**
   * 默认响应报文(针对mq接受这种情况)
   *
   * @return
   */
  String responseMsg() default "";

}
