package com.elasticsearch.spring.demo;

import com.elasticsearch.spring.demo.util.EsConsts;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class MybatisSpringDemoApplicationTests {

    @Autowired
    RestHighLevelClient client;

    @Test
    public void createIndex() throws IOException {
        // 创建索引 - 请求对象
        CreateIndexRequest request = new CreateIndexRequest(EsConsts.INDEX_NAME);
        // 发送请求，获取响应
        CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);
        boolean acknowledged = response.isAcknowledged();
        // 响应状态
        System.out.println("操作状态 = " + acknowledged);
    }
}
