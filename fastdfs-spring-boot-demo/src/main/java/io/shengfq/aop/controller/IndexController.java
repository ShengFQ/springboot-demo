package io.shengfq.aop.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

import io.shengfq.aop.common.Request;
import io.shengfq.aop.service.UploadService;

/**
 * ClassName: IndexController Description: 基本controller
 *
 * @author shengfq
 * @date: 2023/3/26 12:30 下午
 */
@RestController
@Scope("prototype")
public class IndexController {
  @Autowired
  public UploadService uploadService;

  @GetMapping(value = "/login")
  public String login(@RequestParam(value = "sign") String sign,
      @RequestParam(value = "timestamp") String timestamp,
      @RequestParam(value = "data") String data) throws Exception {
    Request request = new Request();
    request.setData(data);
    request.setTimestamp(timestamp);
    request.setSign(sign);
    return JSONObject.toJSONString(request);
  }

  @GetMapping(value = "/index/{resource}")
  public String index(@PathVariable(value = "resource") String resource) {
    System.out.println("resource:" + resource);
    return resource;
  }

  @ResponseBody
  @PostMapping(value = "/upload")
  public String uploadFace(MultipartFile file) throws Exception {
    // 获取上传文件的名称
    String originalFilename = file.getOriginalFilename();
    if (StringUtils.isBlank(originalFilename)) {
      return "文件不能为空，请选择一个文件再上传！";
    }
    String[] fileNameArr = originalFilename.split("\\.");
    // 文件后缀
    String suffix = fileNameArr[fileNameArr.length - 1];
    // 判断后缀是否符合
    if (!suffix.equalsIgnoreCase("png") && !suffix.equalsIgnoreCase("jpg")
        && !suffix.equalsIgnoreCase("jpeg")) {
      return "文件图片格式不支持！";
    }
    String filePath = uploadService.uploadFastDfs(file, suffix);
    if (StringUtils.isBlank(filePath)) {
      return "文件上传失败！";
    }
    return filePath;
  }
}
