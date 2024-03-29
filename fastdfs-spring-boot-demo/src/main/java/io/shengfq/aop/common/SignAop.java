package io.shengfq.aop.common;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Aspect
@Component
public class SignAop {

  /**
   * 声明一个切入点，范围为controller包下所有的类 注：作为切入点签名的方法必须返回void类型
   */
  @Pointcut("execution(public * io.shengfq.aop.controller.*.*(..))")
  private void signAop() {

  }

  /**
   * 前置通知：在某连接点之前执行的通知，但这个通知不能阻止连接点之前的执行流程（除非它抛出一个异常）
   *
   * @param joinPoint
   * @throws Exception
   */
  @Before("signAop()")
  public void doBefore(JoinPoint joinPoint) throws Exception {
    Object[] objects = joinPoint.getArgs();
    String sign = objects[0].toString();
    String timestamp = objects[1].toString();
    String data = objects[2].toString();

    if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(timestamp) || StringUtils.isEmpty(data)) {
      throw new GlobalException("sign or timestamp or data is null");
    }

    String md5String = "data=" + data + "&key=1234567890&timestamp=" + timestamp;
    String signNow = DigestUtils.md5Hex(md5String);

    if (!sign.equalsIgnoreCase(signNow)) {
      throw new GlobalException("sign is error");
    }
    // 日志记录aop
    String strLog = "[" + Thread.currentThread().getId() + "]" + "[请求方法] "
        + joinPoint.getSignature().getName() + " ||";
    LoggerUtils.getLogger()
        .info(strLog + "[请求参数] sign=" + sign + ",timestamp=" + timestamp + ",data=" + data);
  }

  /**
   * 后置通知：在某连接点正常完成后执行的通知，通常在一个匹配的方法返回的时候执行
   *
   * @param joinPoint
   * @param params
   * @return
   */
  @AfterReturning(value = "signAop()", returning = "params")
  public JSONObject doAfterReturning(JoinPoint joinPoint, JSONObject params) {
    String data = params.getString(Request.DATA);
    long timestamp = System.currentTimeMillis() / 1000;
    String md5String = "data=" + data + "&key=1234567890&timestamp=" + timestamp;
    String sign = DigestUtils.md5Hex(md5String);
    params.put(Request.TIMESTAMP, timestamp);
    params.put(Request.SIGN, sign);
    // 日志记录aop
    String strLog = "[" + Thread.currentThread().getId() + "]" + "[请求方法] "
        + joinPoint.getSignature().getName() + " ||";
    LoggerUtils.getLogger().info(strLog + "[返回参数] " + params);
    return params;
  }

}
