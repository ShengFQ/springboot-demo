package io.shengfq.fastdfs.aop;

import io.shengfq.fastdfs.anno.MyTransaction;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 基于注解连接点的AOP切面实现
 * @date 2023-04-14
 * */
@Aspect
@Component
public class MyTransactionAspect {
    //切入点：增强标有MyLogAnnotation注解的方法
    @Pointcut(value="@annotation(io.shengfq.fastdfs.anno.MyTransaction)")
    public void transactionAnnotation(){
        System.out.println("pointCut签名。。。");
    }
    //前置通知
    //注意：获取注解中的属性时，@annotation()中的参数要和deBefore方法中的参数写法一样，即myLogAnnotation
    @Before("transactionAnnotation() && @annotation(myTransaction)")
    public void deBefore(JoinPoint jp, MyTransaction myTransaction) throws Throwable {
     	System.out.println("前置通知：方法执行前执行...");
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取注解中的属性
        System.out.println("deBefore===========>" + myTransaction.desc());
        // 记录下请求内容
        System.out.println("URL : " + request.getRequestURL().toString());
    }

    /**
     * 环绕通知,环绕增强，相当于MethodInterceptor
     * 语法上必须按照这种方法
     * */
    @Around("transactionAnnotation() && @annotation(myTransaction)")
    public Object aroundAdvice(ProceedingJoinPoint pjp,MyTransaction myTransaction) {
        Object rtValue = null;
        try {
            // 获取注解中的属性
            System.out.println("aroundAnnotationAdvice===========>" + myTransaction.desc());
            Object[] args = pjp.getArgs();
            //得到方法执行所需的参数
            System.out.println("通知类中的aroundAdvice方法执行了。。前置");
            //明确调用切入点方法（切入点方法）
            rtValue = pjp.proceed(args);
            System.out.println("通知类中的aroundAdvice方法执行了。。返回");
            System.out.println("返回通知："+rtValue);
            return rtValue;
        } catch (Throwable e) {
            System.out.println("通知类中的aroundAdvice方法执行了。。异常");
            throw new RuntimeException(e);
        } finally {
            System.out.println("通知类中的aroundAdvice方法执行了。。后置");
        }
    }
}
