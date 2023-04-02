package com.shengfq.mqttclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * ClassName: emqx broker webhook params test
 * Description: 基本controller
 *
 * @author shengfq
 * @date: 2023/3/26 12:30 下午
 */
@RestController
@Slf4j
public class IndexController {


    @GetMapping(value = "/index")
    public String index(){
        return "hello";
    }
    /**
     * config:emqx_web_hook.conf
     * action:on_client_connect  client.connect
     *  {username=, proto_ver=5, node=emqx@127.0.0.1, keepalive=60, ipaddress=127.0.0.1, clientid=mqttx_4f3aec59, action=client_connect}
     * action:on_message_publish message.publish
     *  {ts=1680259872063, topic=t/1, retain=true, qos=0, payload=ewogICJtc2ciOiAiaGVsbG8iCn0=, node=emqx@127.0.0.1, from_username=, from_client_id=mqttx_4f3aec59, action=message_publish} //payload被base64编码传输
     *  action:client_connect client_connected client_connack
     * */
    @ResponseBody
    @PostMapping(value = "/emqx_web_hook")
    public String msg(@RequestBody Map<String,Object> req) throws Exception {
        log.info("params:{}",req);
        return "ok";
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request)throws Exception{
        log.info("ip:{} thread:{}",request.getRemoteAddr(),Thread.currentThread().getName());
        Thread.sleep(500);
        return "ok";
    }
}
