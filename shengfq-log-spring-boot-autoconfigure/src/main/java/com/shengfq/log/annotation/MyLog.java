package com.shengfq.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author sheng
 * @date 2024-02-14
 * @version 1.0
 * @desc 自定义日志注解
 * 当注解的方法被调用,就打印日志
 * */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLog {
    String desc() default "";
}
