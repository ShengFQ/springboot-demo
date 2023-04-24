package com.elasticsearch.spring.demo.controller;

import com.elasticsearch.spring.demo.dto.req.NewsSearchView;
import com.elasticsearch.spring.demo.dto.req.WebContentReq;
import com.elasticsearch.spring.demo.entity.WebContent;
import com.elasticsearch.spring.demo.service.IndexService;
import com.elasticsearch.spring.demo.service.WebContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * ClassName: IndexController
 * Description:
 *
 * @author shengfq
 * @date: 2022/8/30 2:51 下午
 */
@RestController
public class IndexController {
    @Autowired
    private WebContentService webContentService;
    @Autowired
    private IndexService indexService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/index/create")
    public boolean indexCreate() throws IOException {
        return indexService.createIndex();
    }

    @GetMapping("/index/delete")
    public boolean indexDelete() throws IOException {
        return indexService.deleteIndex();
    }

    /**
     * 爬虫数据保存
     * 通过关键字获取匹配的商品列表数据保存到es
     * https://search.jd.com/Search?keyword=java
     */
    @GetMapping("/save/{keyword}")
    public boolean save(@PathVariable("keyword") String keyword) throws Exception {
        return webContentService.save(keyword);
    }

    /**
     * 爬虫数据删除
     */
    @GetMapping("/delete/{keyword}")
    public int deleteByKeyword(@PathVariable("keyword") String keyword) throws Exception {
        return webContentService.delete(keyword);
    }

    /**
     * 爬虫数据批量删除
     */
    @GetMapping("/deleteMore")
    public int deleteBatch() throws Exception {
        return webContentService.deleteBatch();
    }

    /**
     * 爬虫数据搜索
     */
    @GetMapping("/search/{keyword}/{pageNo}/{pageSize}")
    public List<Map<String, Object>> search(@PathVariable("keyword") String keyword, @PathVariable("pageNo") int pageNo, @PathVariable("pageSize") int pageSize) throws Exception {
        return webContentService.search(keyword, pageNo, pageSize);
    }

    /**
     * 所有数据
     */
    @GetMapping("/all")
    public List<WebContent> all() throws Exception {
        return webContentService.all();
    }

    /**
     * 组合查询
     */
    @GetMapping("/searchbycondition/{keyword}")
    public List<Map<String, Object>> searchByCondition(@PathVariable("keyword") String keyword) throws Exception {
        return webContentService.searchByCondition(keyword);
    }

    /**
     * 分组查询
     */
    @GetMapping("/searchbygroup")
    public List<Map<String, Object>> searchByGroup() throws Exception {
        return webContentService.searchByGroup();
    }

    @PostMapping("do/s")
    public NewsSearchView search(@RequestBody WebContentReq newsSearchDto) {
        NewsSearchView newsSearchView = webContentService.search(newsSearchDto);
        return newsSearchView;
    }
}
