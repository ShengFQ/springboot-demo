package io.shengfq.fastdfs.common;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
/**
 * @Aspect 当前类是个切面类
 * @author sheng
 * @date 2023-04-13
 * */
@Aspect
@Component
public class SignAop {

    /**
     * 声明一个切入点，范围为controller包下所有的类
     * 注：作为切入点签名的方法必须返回void类型
     */
    @Pointcut("execution(public * io.shengfq.fastdfs.controller.*.*(..))")
    private void signAop() {
        System.out.println("--------pointcut------");
    }

    /**
     * 前置通知：在某连接点之前执行的通知，但这个通知不能阻止连接点之前的执行流程（除非它抛出一个异常）
     *
     * @param joinPoint
     * @throws Exception
     */
    @Before("signAop()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        System.out.println("-------这是前置通知-------");
        Object[] objects = joinPoint.getArgs();
        String sign = objects[0].toString();
        String timestamp = objects[1].toString();
        String data = objects[2].toString();

        if (StringUtils.isEmpty(sign) || StringUtils.isEmpty(timestamp) ||
                StringUtils.isEmpty(data)) {
            throw new GlobalException("sign or timestamp or data is null");
        }

        String md5String = "data=" + data + "&key=1234567890&timestamp=" + timestamp;
        String signNow = DigestUtils.md5Hex(md5String);

        if (!sign.equalsIgnoreCase(signNow)) {
            throw new GlobalException("sign is error");
        }
        //日志记录aop
        String strLog = "[" + Thread.currentThread().getId() + "]" + "[请求方法] " + joinPoint.getSignature().getName() + " ||";
        LoggerUtils.getLogger().info(strLog + "[请求参数] sign=" + sign + ",timestamp=" + timestamp + ",data=" + data);
    }

    /**
     * 返回通知：在某连接点正常完成后执行的通知，通常在一个匹配的方法返回的时候执行
     * 本方法是做了增强,把参数值又添加到返回值列表
     * @param joinPoint
     * @param params
     * @return
     */
    @AfterReturning(pointcut = "signAop()", returning = "params")
    public JSONObject doAfterReturning(JoinPoint joinPoint, JSONObject params) {
        System.out.println("-------这是返回通知doAfterReturning()-------");
        String data = params.getString(Request.DATA);
        long timestamp = System.currentTimeMillis() / 1000;
        String md5String = "data=" + data + "&key=1234567890&timestamp=" + timestamp;
        String sign = DigestUtils.md5Hex(md5String);
        params.put(Request.TIMESTAMP, timestamp);
        params.put(Request.SIGN, sign);
        //日志记录aop
        String strLog = "[" + Thread.currentThread().getId() + "]" + "[请求方法] " + joinPoint.getSignature().getName() + " ||";
        LoggerUtils.getLogger().info(strLog + "[返回参数] " + params);
        return params;
    }
    /**
     * 后置通知,
     * */
    @After(value = "signAop()",argNames = "joinPoint")
    public void doAfter(JoinPoint joinPoint) throws Exception{
        System.out.println("-------这是后置通知doAfter()-------");
    }

    /**
     * 异常通知
     * */
    @AfterThrowing(throwing = "ex", pointcut = "signAop()")
    public void throwss(JoinPoint jp, Exception ex) {
        System.out.println("异常通知：方法异常时执行.....");
        System.out.println("产生异常的方法：" + jp);
        System.out.println("异常种类：" + ex);
    }
    /**
     * 环绕通知，围绕着被切入方法执行。参数必须为ProceedingJoinPoint，pjp.proceed相应于执行被切面的方法。环绕通知一般单独使用，环绕通知可以替代上面的四种通知，后面单独介绍
     * */
    /*@Around(value = "signAop()",argNames = "point")
    public void aroundAdv(ProceedingJoinPoint point){
        System.out.println("------------环绕通知--------");
    }*/

}
