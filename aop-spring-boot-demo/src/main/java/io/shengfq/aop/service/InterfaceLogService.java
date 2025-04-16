package io.shengfq.aop.service;

import org.springframework.stereotype.Service;

import io.shengfq.aop.domain.vo.InterfaceLogVo;
import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: InterfaceLogService Description:
 *
 * @author shengfq
 * @date: 2023/9/25 9:29 下午
 */
@Service
@Slf4j
public class InterfaceLogService {

  public void writeLog(InterfaceLogVo interfaceLogVo) {
    log.info("print log:{}", interfaceLogVo);
  }
}
