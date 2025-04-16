package com.shengfq.toolkit.debounce;

import java.lang.annotation.*;

/**
 * @description 加上这个注解可以将参数设置为key
 */
@Target({

    ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RequestKeyParam {
}
