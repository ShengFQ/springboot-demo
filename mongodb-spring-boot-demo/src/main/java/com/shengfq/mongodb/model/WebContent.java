package com.shengfq.mongodb.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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
@Document(value = "web_content")
public class WebContent implements Serializable {

    @Field(name = "_id")
    private String id;
    @Indexed
    private String img;
    @Indexed
    private String price;
    @Indexed
    private String title;
    /**
     * 评价数量
     */
    private String commit;
    /**
     * 商品店
     */
    private String shop;
    /**
     * 商品供货地
     */
    private String province;
    /**
     * 标签
     * */
    private String tags;
}
