package io.shengfq.fastdfs.controller;

import com.alibaba.fastjson.JSONObject;
import io.shengfq.fastdfs.common.Request;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: IndexController
 * Description: 基本controller
 *
 * @author shengfq
 * @date: 2023/3/26 12:30 下午
 */
@RestController
@Scope("prototype")
public class IndexController {

    @GetMapping(value = "/login")
    public String login(
            @RequestParam(value = "sign") String sign,
            @RequestParam(value = "timestamp") String timestamp,
            @RequestParam(value = "data") String data
    ) throws Exception {
        Request request = new Request();
        request.setData(data);
        request.setTimestamp(timestamp);
        request.setSign(sign);
        return JSONObject.toJSONString(request);
    }


}
