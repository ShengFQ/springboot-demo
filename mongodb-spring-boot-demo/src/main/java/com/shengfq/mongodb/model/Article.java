package com.shengfq.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

/**
 * <p>
 * 文章实体类
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2018-12-28 16:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collation = "article")
public class Article {
    /**
     * 文章id
     */
    @Id
    private String id;

    /**
     * 文章标题
     */
    @Indexed
    private String title;

    /**
     * 文章内容
     */

    private String content;

    /**
     * 创建时间
     */
    @Field("create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Field("update_time")
    private Date updateTime;

    /**
     * 点赞数量
     */
    @Field("thumb_up")
    private Long thumbUp;

    /**
     * 访客数量
     */
    private Long visits;

}
