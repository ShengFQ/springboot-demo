package com.elasticsearch.spring.demo.service;

import com.elasticsearch.spring.demo.util.EsConsts;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;

/**
 * ClassName: IndexService
 * Description: es索引操作
 *
 * @author shengfq
 * @date: 2022/9/8 11:58 上午
 */
@Slf4j
@Service
public class IndexService {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 创建索引
     */
    public boolean createIndex() throws IOException {
        //创建索引 --- 请求对象
        CreateIndexRequest request = new CreateIndexRequest(EsConsts.INDEX_NAME);
        //发送请求 --- 获取响应
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        //响应状态
        boolean acknowledged = response.isAcknowledged();
        log.info("添加索引：{}", response.index());
        return acknowledged;
    }

    /**
     * 查看索引
     */
    public String getIndex() throws IOException {
        //查询索引 --- 请求对象
        GetIndexRequest request = new GetIndexRequest();
        request.indices(EsConsts.INDEX_NAME_DEMO);
        //发送请求 --- 获取响应
        GetIndexResponse response = restHighLevelClient.indices().get(request, RequestOptions.DEFAULT);
        //响应状态
        System.out.println(response.getAliases());
        System.out.println(response.getMappings());
        System.out.println(response.getSettings());
        Arrays.stream(response.getIndices()).forEach(System.out::println);
        return response.toString();
    }

    /**
     * 删除索引
     */
    public boolean deleteIndex() throws IOException {
        //删除索引 --- 请求对象
        DeleteIndexRequest request = new DeleteIndexRequest(EsConsts.INDEX_NAME);
        request.indices(EsConsts.INDEX_NAME);
        //发送请求 --- 获取响应
        AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        //响应状态
        System.out.println(response.isAcknowledged());
        return response.isAcknowledged();
    }


}
