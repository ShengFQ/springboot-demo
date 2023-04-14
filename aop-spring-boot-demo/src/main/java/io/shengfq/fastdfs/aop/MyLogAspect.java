package io.shengfq.fastdfs.aop;

import io.shengfq.fastdfs.anno.MyLogAnnotation;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
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
public class MyLogAspect {
    //切入点：增强标有MyLogAnnotation注解的方法
    @Pointcut(value="@annotation(io.shengfq.fastdfs.anno.MyLogAnnotation)")
    //切入点签名
    public void logAnnotation(){
        System.out.println("pointCut签名。。。");
    }
    //前置通知
    //注意：获取注解中的属性时，@annotation()中的参数要和deBefore方法中的参数写法一样，即myLogAnnotation
    @Before("logAnnotation() && @annotation(myLogAnnotation)")
    public void deBefore(JoinPoint jp, MyLogAnnotation myLogAnnotation) throws Throwable {
     	System.out.println("前置通知：方法执行前执行...");
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 获取注解中的属性
        System.out.println("deBefore===========>" + myLogAnnotation.desc());
        // 记录下请求内容
        System.out.println("URL : " + request.getRequestURL().toString());
    }
    //返回通知
    @AfterReturning(returning = "ret", pointcut = "logAnnotation() && @annotation(myLogAnnotation)")
    public void doAfterReturning(Object ret, MyLogAnnotation myLogAnnotation) throws Throwable {
        // 获取注解中的属性
        System.out.println("doAfterReturning===========>" + myLogAnnotation.desc());
        // 处理完请求，返回内容
        System.out.println("返回通知：方法的返回值 : " + ret);
    }

    //异常通知
    @AfterThrowing(throwing = "ex", pointcut = "logAnnotation() && @annotation(myLogAnnotation)")
    public void throwss(JoinPoint jp,Exception ex, MyLogAnnotation myLogAnnotation){
        // 获取注解中的属性
        System.out.println("throwss===========>" + myLogAnnotation.desc());
        System.out.println("异常通知：方法异常时执行.....");
        System.out.println("产生异常的方法："+jp);
        System.out.println("异常种类："+ex);
    }

    //后置通知
    @After("logAnnotation() && @annotation(myLogAnnotation)")
    public void after(JoinPoint jp, MyLogAnnotation myLogAnnotation){
        // 获取注解中的属性
        System.out.println("after===========>" + myLogAnnotation.desc());
        System.out.println("后置通知：最后且一定执行.....");
    }
}
