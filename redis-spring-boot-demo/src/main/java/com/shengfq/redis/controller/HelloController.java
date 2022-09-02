package com.shengfq.redis.controller;

import com.shengfq.redis.ratelimit.LimitType;
import com.shengfq.redis.ratelimit.RateLimiter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class HelloController {
    @GetMapping("/hello")
    @RateLimiter(time = 5, count = 3, limitType = LimitType.DEFAULT)
    public String hello() {
        return "hello>>>" + new Date();
    }
}
