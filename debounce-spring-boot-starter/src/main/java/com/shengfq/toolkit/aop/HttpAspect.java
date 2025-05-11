package com.shengfq.toolkit.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "http-log")
@Aspect
@Component
public class HttpAspect {

  @Pointcut("execution(* com.shengfq.toolkit.controller..*.*(..))")
  public void pointCut() {

  }

  @Around(value = "pointCut()")
  public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
    // 获取本次接口的唯一码
    String token = java.util.UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
    MDC.put("traceId", token);

    // result的值就是被拦截方法的返回值
    try {
      // 获取目标参数
      String serviceUniqueName = proceedingJoinPoint.getSignature().getDeclaringTypeName();
      String methodName = proceedingJoinPoint.getSignature().getName();
      long start = System.currentTimeMillis();
      Object proceed = proceedingJoinPoint.proceed();
      log.info("@Http:{}.{},耗时:{}ms", serviceUniqueName, methodName,
          System.currentTimeMillis() - start);
      return proceed;
    } catch (Exception e) {
      log.error("@Http请求出错", e);
      throw e;
    } finally {
      MDC.remove("traceId");
    }
  }
}
