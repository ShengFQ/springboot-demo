package com.shengfq.log.autoconfigure;

import com.shengfq.log.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/**
 * mvc dispatch拦截器注册,要加入到包加载器bootstrap
 * @author shengfq
 * @date 2024-02-14
 * 来源:https://juejin.cn/post/7193996189669261370
 * */
@Configuration
public class LogAutoConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    //注册Log拦截器,当mvc框架
     InterceptorRegistration registration= registry.addInterceptor(new LogInterceptor());
     //拦截地址匹配
     registration.addPathPatterns("/**");
     //白名单
     registration.excludePathPatterns("/**/*.html","/**/*.js","/**/*.css");
    }
}
