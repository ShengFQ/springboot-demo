package com.shengfq.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName: IndexController
 *
 * @author shengfq
 * @date: 2024/8/31 1:50 下午
 */
@RestController
public class IndexController {

  @GetMapping("/index")
  public String index() {
    try {
      Thread.sleep(30 * 1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return "index";
  }

  @GetMapping("/shutdown")
  public void shutdown() {
    System.exit(9);
  }
}
