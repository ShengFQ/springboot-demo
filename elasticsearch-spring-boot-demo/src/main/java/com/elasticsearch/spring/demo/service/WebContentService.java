package com.elasticsearch.spring.demo.service;


import com.alibaba.fastjson.JSON;
import com.elasticsearch.spring.demo.dto.req.NewsSearchView;
import com.elasticsearch.spring.demo.dto.req.WebContentReq;
import com.elasticsearch.spring.demo.entity.WebContent;
import com.elasticsearch.spring.demo.util.EsConsts;
import com.elasticsearch.spring.demo.util.JsoupUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.elasticsearch.spring.demo.util.EsConsts.INDEX_NAME;

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
    RestHighLevelClient restHighLevelClient;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    /**
     * 批量添加文档
     * 搜索关键字爬虫
     */
    public boolean save(String keyword) throws Exception {
        List<WebContent> list = JsoupUtils.parse(keyword);
        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("2m");
        for (int i = 0; i < list.size(); i++) {
            bulkRequest.add(new IndexRequest(INDEX_NAME).type(EsConsts.TYPE_NAME).id(EsConsts.DOC_ID_PREFIX + i).source(JSON.toJSONString(list.get(i)), XContentType.JSON));
        }
        //批量插入数据记录
        BulkResponse responses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !responses.hasFailures();
    }

    /**
     * 根据文档id删除单个文档
     */
    public int delete(String docId) throws IOException {
        DeleteRequest request = new DeleteRequest();
        request.index(INDEX_NAME).type(EsConsts.TYPE_NAME).id(docId);
        DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        byte op = response.getResult().getOp();
        log.info("delete by id result:{}", op);
        return op;
    }

    /**
     * 批量删除文档
     */
    public int deleteBatch() {
        int result = 0;
        BulkRequest request = new BulkRequest();
        for (int i = 0; i < 20; i++) {
            request.add(new DeleteRequest().index(INDEX_NAME).type(EsConsts.TYPE_NAME).id(EsConsts.DOC_ID_PREFIX + i));
        }
        BulkResponse response = null;
        try {
            response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("time:{}", response.getTook());
        if (response != null && response.getItems() != null) {
            //Arrays.stream(response.getItems()).forEach(System.out::println);
            result = response.getItems().length;
        }
        return result;
    }


    /**
     * 根据关键字检索数据集
     * 条件分页查询
     */
    public List<Map<String, Object>> search(String keyword, int pageNo, int pageSize) throws IOException {
        List<Map<String, Object>> result = new ArrayList<>();
        //创建请求
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //分页参数
        sourceBuilder.from(pageNo);
        sourceBuilder.size(pageSize);
        //精准匹配模式
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery(EsConsts.PROPERTY_TITLE, keyword);
        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        //执行搜索
        searchRequest.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //解析结果
        for (SearchHit searchHit : response.getHits().getHits()) {
            result.add(searchHit.getSourceAsMap());
        }
        return result;
    }

    /**
     * 根据关键字检索数据集
     * 全量查询,排序,过滤字段
     */
    public List<WebContent> all() throws IOException {
        List<WebContent> result = new ArrayList<>();
        //1.查询索引中的全部文档 --- matchAllQuery 全量查询
        //创建搜索请求对象
        SearchRequest request = new SearchRequest();
        //设置参数 --- 表示查询哪个索引中的文档内容
        request.indices(INDEX_NAME);
        request.types((EsConsts.TYPE_NAME));
        //构建查询的请求体 --- 存入搜索请求对象中
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(QueryBuilders.matchAllQuery());
        //设置排序字段
        //sourceBuilder.sort(EsConsts.PROPERTY_TITLE);
        //设置过滤字段
        String[] excludes = {EsConsts.PROPERTY_COMMIT};
        String[] includes = {};
        sourceBuilder.fetchSource(includes, excludes);
        request.source(sourceBuilder);

        //发送请求 --- 获取响应
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        //获取查询到的结果集
        SearchHits hits = response.getHits();
        log.info("结果集的条数 {}", hits.getTotalHits()); //结果集的条数
        log.info("总耗时 {} ", response.getTook());  //总耗时
        //遍历结果集
        //解析结果
        for (SearchHit searchHit : response.getHits().getHits()) {
            result.add(JSON.parseObject(searchHit.getSourceAsString(),WebContent.class));
        }
        return result;
    }

    /**
     * 组合条件查询
     */
    public List<Map<String, Object>> searchByCondition(String keyword) throws IOException {
        List<Map<String, Object>> result = new ArrayList<>();
        //创建请求
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //组合条件查询匹配模式
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.should(QueryBuilders.matchQuery(EsConsts.PROPERTY_TITLE, keyword));
        sourceBuilder.query(boolQueryBuilder);
        sourceBuilder.timeout(new TimeValue(50, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //解析结果
        for (SearchHit searchHit : response.getHits().getHits()) {
            result.add(searchHit.getSourceAsMap());
        }
        return result;
    }

    /**
     * 根据age年龄字段进行group分组查询
     */
    public List<Map<String, Object>> searchByGroup() throws IOException {
        List<Map<String, Object>> result = new ArrayList<>();
        //创建请求
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //组合条件查询匹配模式
        AggregationBuilder aggregationBuilder = AggregationBuilders.terms("provinceGroup").field(EsConsts.PROPERTY_PROVINCE);
        sourceBuilder.aggregation(aggregationBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        //解析结果
        for (SearchHit searchHit : response.getHits().getHits()) {
            result.add(searchHit.getSourceAsMap());
        }
        return result;
    }


    /**
     * 分词查询
     *
     * @param newsSearchDto
     * @return
     */
    public NewsSearchView search(WebContentReq newsSearchDto) {
        Integer pageSize = 10;
        //创建bool查询
        BoolQueryBuilder filterCaseBuilder = QueryBuilders.boolQuery();

        //分词查询
        BoolQueryBuilder query = QueryBuilders.boolQuery();
        query.should(QueryBuilders.matchQuery("title", newsSearchDto.getTitle()));
        query.should(QueryBuilders.matchQuery("content", newsSearchDto.getTitle()));

        //条件过滤
        filterCaseBuilder.filter(QueryBuilders.termQuery("status", 1));
        filterCaseBuilder.filter(QueryBuilders.rangeQuery("add_date").lte(LocalDate.now()));
        filterCaseBuilder.must(query);

        //排序
        ScoreSortBuilder scoreSortBuilder = new ScoreSortBuilder();
        FieldSortBuilder addDateSortBuilder = SortBuilders.fieldSort("year").order(SortOrder.DESC);

        //分词高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("title").field("content").preTags("<span class=\"highlight\">").postTags("</span>");

        //分页
        Integer page = newsSearchDto.getPageNo() / pageSize;
        Pageable pageOf = PageRequest.of(page, pageSize);

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(filterCaseBuilder)
                .withSort(addDateSortBuilder)
                .withSort(scoreSortBuilder)
                .withHighlightBuilder(highlightBuilder)
                .withPageable(pageOf)
                .build();

        NewsSearchView pageInfo = new NewsSearchView();
        /*try {
            List<WebContent> newsList = elasticsearchRestTemplate.query(searchQuery, response -> {
                SearchHits hits = response.getHits();
                long totalHits = hits.totalHits;
                int totalPage = PageUtil.totalPage((int) totalHits, pageSize);
                pageInfo.setTotalNum(totalHits);
                pageInfo.setTotalPage(totalPage);
                List<NewsView> list = new ArrayList<>();
                Arrays.stream(hits.getHits()).forEach(hit -> {
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    dealHighlight(hit, sourceAsMap);
                    NewsView aopHandlerLogRsp = JSON.parseObject(JSON.toJSONString(sourceAsMap), NewsView.class);
                    list.add(aopHandlerLogRsp);
                });
                return list;
            });
            pageInfo.setList(newsList);
        } catch (ElasticsearchException e) {
            log.error("ES查询错误", e);
        }*/
        return pageInfo;
    }
}
