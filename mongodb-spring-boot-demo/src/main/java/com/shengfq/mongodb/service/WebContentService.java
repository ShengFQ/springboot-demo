package com.shengfq.mongodb.service;


import com.shengfq.mongodb.model.WebContent;
import com.shengfq.mongodb.util.JsoupUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * ClassName: WebContentService
 * Description: 京东商品爬虫
 *
 * @author shengfq
 * @date: 2022/8/30 3:29 下午
 */
@Slf4j
@Service
public class WebContentService {

    @Autowired
    private MongoTemplate mongoTemplate;
    /**
     * 批量添加文档
     * 搜索关键字爬虫
     */
    @Transactional
    public boolean save(String keyword) throws Exception {
        List<WebContent> list = JsoupUtils.parse(keyword);
        for (int i = 0; i < list.size(); i++) {
            mongoTemplate.save(list.get(i));
        }
        return true;
    }

    public List<WebContent> findAll(){
        return mongoTemplate.findAll(WebContent.class);
    }
    /**
     * 序列化是怎么实现的
     * */
    public WebContent getById(String id){
        Query query=new Query(Criteria.where("id").is(id));
        return mongoTemplate.findOne(query,WebContent.class);
    }

    public List<WebContent> getByTitle(String title){
        Query query=new Query(Criteria.where("title").is(title));
        return mongoTemplate.find(query,WebContent.class);
    }

    public String delete(WebContent webContent){
        mongoTemplate.remove(webContent);
        return "success";
    }

    public String deleteById(String id){
        WebContent webContent=getById(id);
        delete(webContent);
        return "success";
    }
}
