package com.shengfq.log;

import com.shengfq.log.annotation.MyLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: IndexController
 * Description: 测试注解拦截器组件
 *
 * @author shengfq
 * @date: 2024/2/15 11:24 上午
 */

@RestController
public class IndexController {
    @MyLog(desc = "进入index方法")
    @GetMapping("/index")
    public String index(@RequestParam String context){
        return "hello~ "+context;
    }
}
