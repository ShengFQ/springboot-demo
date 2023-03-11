package com.elasticsearch.spring.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 这是一个新建的类
 * 其中，注解解释如下：
 * @Document(indexName="blob3",type="article")：
 *     indexName：索引的名称（必填项）
 *     type：索引的类型
 * @Id：主键的唯一标识
 * @Field(index=true,analyzer="ik_smart",store=true,searchAnalyzer="ik_smart",type = FieldType.text)
 *     index：是否设置分词
 *     analyzer：存储时使用的分词器
 *     searchAnalyze：搜索时使用的分词器
 *     store：是否存储
 *     type: 数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "sdes_blog",type="article",shards = 1,replicas = 0)
public class Article {
    @Id
    @Field(type = FieldType.Long, store = true)
    private long id;
    @Field(type = FieldType.Text, store = true, analyzer = "ik_smart")
    private String title;
    @Field(type = FieldType.Text, store = true, analyzer = "ik_smart")
    private String content;
}
