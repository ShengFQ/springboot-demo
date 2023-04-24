package com.elasticsearch.spring.demo.dao;

import com.elasticsearch.spring.demo.entity.WebContent;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface WebContentRepository extends ElasticsearchRepository<WebContent,Long> {
    //自定义查询条件方法
    //命名符合规范 不需要实现方法 spring会为我们实现
    List<WebContent> findByTitle(String title);//自定义条件查询
    List<WebContent> findByTitleOrContent(String title,String Content);//自定义条件查询
    List<WebContent> findByTitleOrContent(String title, String Content, Pageable pageable);//自定义条件查询 设置分页
}
