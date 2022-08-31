package com.elasticsearch.spring.demo.controller;

import com.elasticsearch.spring.demo.service.WebContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * ClassName: IndexController
 * Description:
 * @author shengfq
 * @date: 2022/8/30 2:51 下午
 */
@RestController
public class IndexController {
    @Autowired
    private WebContentService webContentService;
    @GetMapping("/")
    public String index(){
        return "index";
    }

    /**
     * 爬虫 爬网页
     * https://search.jd.com/Search?keyword=java
     * */
    @GetMapping("/save/{keyword}")
    public boolean save(@PathVariable("keyword") String keyword) throws Exception{
      return  webContentService.save(keyword);
    }

    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    public List<Map<String,Object>> search(@PathVariable("keyword") String keyword, @PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) throws Exception{
        return  webContentService.search(keyword,pageNo,pageSize);
    }
}
