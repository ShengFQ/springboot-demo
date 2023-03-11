package com.elasticsearch.spring.demo;

import com.elasticsearch.spring.demo.entity.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 这是一个新建的类
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringDataEsTemplateTest extends ElasticSearchApplicationTests{

    @Autowired
    private ElasticsearchTemplate template;

    @Test
    public void creatIndex() throws Exception {
        //创建索引，并配置映射关系
        template.createIndex(Article.class);
        //配置映射关系
        template.putMapping(Article.class);
    }

    /**
     * 测试 ElasticTemplate 删除 index
     */
    @Test
    public void testDeleteIndex() {
        template.deleteIndex(Article.class);
    }

}
