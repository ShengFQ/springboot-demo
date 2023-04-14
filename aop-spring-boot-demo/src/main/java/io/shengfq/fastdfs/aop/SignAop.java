package io.shengfq.fastdfs.aop;

import com.alibaba.fastjson.JSONObject;
import io.shengfq.fastdfs.common.GlobalException;
import io.shengfq.fastdfs.common.LoggerUtils;
import io.shengfq.fastdfs.common.Request;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @Aspect 基于execution表达式切入连接点的切面实现
 * @author sheng
 * @date 2023-04-13
 * */
@Aspect
@Component
public class SignAop {

    /**
     * 其中execution用于使用切面的连接点。
     * 声明一个切入点，范围为controller包下所有的类
     * 注：作为切入点签名的方法必须返回void类型
     */
    @Pointcut("execution(public * io.shengfq.fastdfs.controller.*.*(..))")
    private void signAop() {
    }

    /**
     * 前置通知：在某连接点之前执行的通知，但这个通知不能阻止连接点之前的执行流程（除非它抛出一个异常）
     *
     * @param joinPoint 连接点对象,通俗点就是被切入方法引用
     * @throws Exception
     */
    @Before("signAop()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        System.out.println("-------这是前置通知-------");

        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        System.out.println("URL : " + request.getRequestURL().toString());
        System.out.println("HTTP_METHOD : " + request.getMethod());
        System.out.println("CLASS_METHOD : " + joinPoint);
        System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));
        System.out.println("前置通知：方法执行前执行...");

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
     * @param joinPoint 连接点对象,通俗点就是被切入方法引用
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
     * 后置通知,一定会执行
     * */
    @After(value = "signAop()",argNames = "joinPoint")
    public void doAfter(JoinPoint joinPoint) throws Exception{
        System.out.println("-------这是后置通知doAfter()-------");
    }

    /**
     * 异常通知
     * 在此注解中可以使用throwing = "XXX"获取异常信息，获取的异常信息可以在方法中打印出来，举例如下。
     * 返回通知和异常通知只能有一个会被执行，因为发生异常执行异常通知，然后就不会继续向下执行，自然后置通知也就不会被执行，反之亦然。
     * */
   /* @AfterThrowing(throwing = "ex", pointcut = "signAop()")
    public void throwss(JoinPoint jp, Exception ex) {
        System.out.println("异常通知：方法异常时执行.....");
        System.out.println("产生异常的方法：" + jp);
        System.out.println("异常种类：" + ex);
    }*/
    /**
     * 环绕通知，围绕着被切入方法执行。参数必须为ProceedingJoinPoint，pjp.proceed相应于执行被切面的方法。环绕通知一般单独使用，环绕通知可以替代上面的四种通知，后面单独介绍
     * */
    /*@Around(value = "signAop()",argNames = "point")
    public void aroundAdv(ProceedingJoinPoint point){
        System.out.println("------------环绕通知--------");
    }*/

}
