package io.shengfq.aop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.shengfq.aop.publish.CustomSpringEventPublisher;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@EnableAsync
@EnableTransactionManagement
@SpringBootApplication
public class AopSpringBootDemoApplication {
  @Autowired
  CustomSpringEventPublisher customSpringEventPublisher;

  public static void main(String[] args) {
    try {
      SpringApplication.run(AopSpringBootDemoApplication.class, args);
      log.debug("aop-spring-boot-application 启动成功了");
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
