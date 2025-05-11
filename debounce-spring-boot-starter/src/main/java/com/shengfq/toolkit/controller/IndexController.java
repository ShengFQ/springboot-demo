package com.shengfq.toolkit.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shengfq.toolkit.debounce.RequestKeyParam;
import com.shengfq.toolkit.debounce.RequestLock;
import com.shengfq.toolkit.pojo.AddReq;

import lombok.extern.slf4j.Slf4j;

/**
 * ClassName: IndexController Description:
 *  测试接口防抖功能
 * @author shengfq
 * @date: 2025/4/16 19:25
 */
@Slf4j
@RestController
public class IndexController {
  @PostMapping("/add")
  @RequestKeyParam
  @RequestLock(prefix = "123")
  public ResponseEntity<String> add(@RequestBody AddReq addReq) {
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }

  @GetMapping("/log")
  public ResponseEntity<String> pringLog() {
    log.debug("this is debug trace {}", new Date());
    log.warn("this is warn trace {}", new Date());
    log.info("this is info trace {}", new Date());
    return ResponseEntity.ok("");
  }


}
