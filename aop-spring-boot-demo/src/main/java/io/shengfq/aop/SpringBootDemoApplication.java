package io.shengfq.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.shengfq.aop.publish.CustomSpringEventPublisher;

@RestController
@SpringBootApplication
public class SpringBootDemoApplication {
  @Autowired
  CustomSpringEventPublisher customSpringEventPublisher;

  public static void main(String[] args) {
    try {
      SpringApplication.run(SpringBootDemoApplication.class, args);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @GetMapping("/index")
  public String index(@RequestParam String message) {
    customSpringEventPublisher.publishCustomEvent(message);
    return "ok";
  }

}
