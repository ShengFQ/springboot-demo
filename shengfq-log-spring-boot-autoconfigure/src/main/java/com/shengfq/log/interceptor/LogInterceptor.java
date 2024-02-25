package com.shengfq.log.interceptor;

import com.shengfq.log.annotation.MyLog;
import com.shengfq.log.domain.TraceInfo;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @Author: guzq
 * @CreateTime: 2022/07/09 18:16
 * @Description: TODO
 * @Version: 1.0
 */
@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    private static final ThreadLocal<TraceInfo> THREAD_LOCAL = new InheritableThreadLocal();
    /**
     * ResourceHttpRequestHandler cannot be cast to HandlerMethod
     * 原文链接：https://blog.csdn.net/m0_37450089/article/details/118538821
     * */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            MyLog log = handlerMethod.getMethodAnnotation(MyLog.class);

            if (log != null) {
                long start = System.currentTimeMillis();
                TraceInfo traceInfo = new TraceInfo();
                String uri = request.getRequestURI();
                String method = handlerMethod.getMethod().getDeclaringClass() + "#" + handlerMethod.getMethod();

                traceInfo.setStart(start);
                traceInfo.setRequestMethod(method);
                traceInfo.setRequestUri(uri);

                String traceId = UUID.randomUUID().toString().replaceAll("-", "");
                traceInfo.setTraceId(traceId);
                MDC.put(TraceInfo.TRACE_ID, traceId);
                THREAD_LOCAL.set(traceInfo);
                return true;
            }
        } else if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            MyLog logAnnotation = handlerMethod.getMethodAnnotation(MyLog.class);
            if (logAnnotation != null) {
                long end = System.currentTimeMillis();
                TraceInfo traceInfo = THREAD_LOCAL.get();
                long start = traceInfo.getStart();

                log.info("requestUri:{}, requestMethod:{}, 请求耗时:{} ms", traceInfo.getRequestUri(), traceInfo.getRequestMethod(), end - start);
                THREAD_LOCAL.remove();
                return;
            }
        } else if (handler instanceof ResourceHttpRequestHandler) {
            return;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
