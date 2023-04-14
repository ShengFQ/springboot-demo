package io.shengfq.fastdfs.controller;

import com.alibaba.fastjson.JSONObject;
import io.shengfq.fastdfs.anno.MyLogAnnotation;
import io.shengfq.fastdfs.anno.MyTransaction;
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
    /**
     * 如果一个方法被两个切入点切入,例如注解多个
     * */
    @MyLogAnnotation(desc = "测试注解类型:helloAnnotation")
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
    /**
     * 如果一个方法被单个切入点切入,例如注解多个
     * */
    @MyLogAnnotation(desc = "测试注解类型:helloAnnotation")
    @GetMapping(value = "/index")
    public String index(@RequestParam(value = "sign") String sign){
        String value=String.format("参数变量:%",sign);
        return value;
    }

    @MyTransaction(desc = "注解环绕通知")
    @GetMapping("/around")
    public Object helloAround(){
        System.out.println("helloAround执行了。。。");
        return "hello around";
    }

}
