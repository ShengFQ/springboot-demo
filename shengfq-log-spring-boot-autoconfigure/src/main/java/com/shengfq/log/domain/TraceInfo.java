package com.shengfq.log.domain;

import lombok.Data;

/**
 * ClassName: TraceInfo
 * Description: 输出日志对象
 *
 * @author shengfq
 * @date: 2024/2/14 6:12 下午
 */
@Data
public class TraceInfo {
    public static final String TRACE_ID="traceId";
    private Long start;
    private String requestMethod;
    private String requestUri;
    private String traceId;
}
