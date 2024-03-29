package com.elasticsearch.spring.demo.entity;

import com.elasticsearch.spring.demo.util.EsConsts;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * ClassName: WebContent
 * Description: 爬虫爬取的商品信息
 *
 * @author shengfq
 * @date: 2022/8/30 2:56 下午
 */
@Data
@Builder
@Document(indexName = EsConsts.INDEX_NAME, type = EsConsts.TYPE_NAME, shards = 1, replicas = 0)
public class WebContent implements Serializable {
    @Id
    private Long id;
    @Field(type = FieldType.Text)
    private String img;
    @Field(type = FieldType.Text)
    private String price;
    @Field(type = FieldType.Text, store = true, analyzer = "ik_smart")
    private String title;
    /**
     * 评价数量
     */
    @Field(type = FieldType.Text)
    private String commit;
    /**
     * 商品店
     */
    @Field(type = FieldType.Text)
    private String shop;
    /**
     * 商品供货地
     */
    @Field(type = FieldType.Keyword)
    private String province;
    /**
     * 标签
     * */
    @Field(type = FieldType.Keyword)
    private String tags;
}
