package io.shengfq.aop.log;

import java.util.Objects;
import java.util.UUID;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import io.shengfq.aop.domain.enums.LogStatusEnum;
import io.shengfq.aop.domain.vo.InterfaceLogVo;
import io.shengfq.aop.exception.CommonException;
import io.shengfq.aop.service.InterfaceLogService;
import lombok.extern.slf4j.Slf4j;

/**
 * 接口日志AOP(对框架InterfaceLogServiceImpl的包装)
 *
 * @author guoy59
 * @date 2021-12-27 14:44
 * @version 1.0
 */
@Slf4j
@Aspect
@Component
public class InterfaceLogAspect {

  @Autowired
  private InterfaceLogService interfaceLogService;

  /** 以自定义 @InterfaceLog 注解为切点 */
  @Pointcut("@annotation(io.shengfq.aop.log.InterfaceLog)")
  public void interfaceLog() {
    log.debug("InterfaceLog Pointcut");
  }

  /**
   * 在切点之前织入
   *
   * @param pjp
   */
  @Around("interfaceLog()")
  public Object around(ProceedingJoinPoint pjp) {
    Object result = null;
    InterfaceLogVo logVo = new InterfaceLogVo();
    try {
      InterfaceLog logAnnotation =
          ((MethodSignature) pjp.getSignature()).getMethod().getAnnotation(InterfaceLog.class);
      logVo = InterfaceLogVo.builder().logType(logAnnotation.type().getCode())
          .type(logAnnotation.busType().getValue()).sys(logAnnotation.logSys().getCode())
          .startDate(DateUtil.now()).trackingNo(UUID.randomUUID().toString())
          .responseMsg(logAnnotation.responseMsg()).status(LogStatusEnum.READY.getCode()).build();
      InterfaceLogHolder.set(logVo);
      // 执行目标方法
      result = pjp.proceed();
    } catch (CommonException e) {
      log.error("InterfaceLogAspect catch error.", e);
      if (Objects.nonNull(logVo)) {
        logVo.setResponseMsg(e.getMessage());
        logVo.setStatus(LogStatusEnum.FAIL.getCode());
      }
      throw e;
    } catch (Throwable ex) {
      log.error("InterfaceLogAspect catch error.", ex);
      if (Objects.nonNull(logVo)) {
        logVo.setResponseMsg(ex.getMessage());
        logVo.setStatus(LogStatusEnum.FAIL.getCode());
      }
      throw new CommonException(ex.getMessage());
    } finally {
      if (Objects.nonNull(logVo)) {
        log.info("InterfaceLog write ,logType:{}", logVo.getLogType());
        logVo.setEndDate(DateUtil.now());
        if (!Objects.equals(logVo.getStatus(), LogStatusEnum.FAIL.getCode())) {
          logVo.setStatus(LogStatusEnum.FINISH.getCode());
        }
        if (StringUtils.isEmpty(logVo.getRequestMsg())) {
          logVo.setRequestMsg(JSONUtil.toJsonStr(pjp.getArgs()));
        }
        if (StringUtils.isEmpty(logVo.getResponseMsg())) {
          logVo.setResponseMsg(JSONUtil.toJsonStr(result));
        }
        interfaceLogService.writeLog(logVo);
      }
    }
    return result;
  }

}
