package com.elasticsearch.spring.demo.service;


import com.alibaba.fastjson.JSON;
import com.elasticsearch.spring.demo.util.EsConsts;
import com.elasticsearch.spring.demo.util.JsoupUtils;
import com.elasticsearch.spring.demo.util.WebContent;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: WebContentService
 * Description: TODO
 *
 * @author shengfq
 * @date: 2022/8/30 3:29 下午
 */
@Service
public class WebContentService {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    public boolean save(String keyword) throws Exception{
       List<WebContent> list= JsoupUtils.parse(keyword);
        BulkRequest bulkRequest=new BulkRequest();
        bulkRequest.timeout("2m");
        for (int i = 0; i <list.size(); i++) {
            bulkRequest.add(new IndexRequest(EsConsts.INDEX_NAME).type(EsConsts.TYPE_NAME).id("100"+i).source(JSON.toJSONString(list.get(i)), XContentType.JSON));
        }
        //批量插入数据记录
       BulkResponse responses= restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !responses.hasFailures();
    }

    public List<Map<String,Object>> search(String keyword,int pageNo,int pageSize) throws IOException {
        List<Map<String,Object>> result=new ArrayList<>();
        //创建请求
        SearchRequest searchRequest=new SearchRequest(EsConsts.INDEX_NAME);
        SearchSourceBuilder sourceBuilder=new SearchSourceBuilder();
        //分页参数
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);
        //精准匹配模式
        TermQueryBuilder termQueryBuilder= QueryBuilders.termQuery("title",keyword);
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse response=restHighLevelClient.search(searchRequest,RequestOptions.DEFAULT);
        //解析结果
        for (SearchHit searchHit:response.getHits().getHits()){
            result.add(searchHit.getSourceAsMap());
        }
        return result;
    }
}
