package com.shengfq.redis.ratelimit;

/**
 * ClassName: ServiceException
 * Description: TODO
 *
 * @author shengfq
 * @date: 2022/9/1 5:44 下午
 */
public class ServiceException extends Exception {
    private int code;

    public ServiceException(String message) {
        super(message);
    }
}
