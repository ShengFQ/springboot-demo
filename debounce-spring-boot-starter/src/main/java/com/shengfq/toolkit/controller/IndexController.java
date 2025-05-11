package com.shengfq.toolkit.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.shengfq.toolkit.debounce.RequestKeyParam;
import com.shengfq.toolkit.debounce.RequestLock;
import com.shengfq.toolkit.pojo.AddReq;

/**
 * ClassName: IndexController Description:
 *  测试接口防抖功能
 * @author shengfq
 * @date: 2025/4/16 19:25
 */
@RestController
public class IndexController {
  @PostMapping("/add")
  @RequestKeyParam
  @RequestLock(prefix = "123")
  public ResponseEntity<String> add(@RequestBody AddReq addReq) {
    return new ResponseEntity<>("OK", HttpStatus.OK);
  }

  @GetMapping("/login")
  public ModelAndView login() {
    return new ModelAndView("login", HttpStatus.OK);
  }


}
