package com.shengfq.eureka.client;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: IndexController Description: index
 *
 * @author shengfq
 * @date: 2024/4/16 10:37 下午
 */
@RestController
public class IndexController {
  @GetMapping("/index")
  public String index(@RequestParam String message) {
    return message;
  }
}
