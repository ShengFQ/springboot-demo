package com.shengfq.mqttclient.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * ClassName: WebHookBodyReq
 * Description: webhook事件参数
 *
 * @author shengfq
 * @date: 2023/3/31 6:15 下午
 */
@Data
public class WebHookBodyReq implements Serializable {
    private String action;

    private String clientid;

    private String username;

    private String ipaddress;

    private Integer keepalive;

    private Integer proto_ver;
}
