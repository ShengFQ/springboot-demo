package com.mybatis.spring.demo.cms.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName(value = "news_info")
public class NewsInfo {
    @TableField(value = "id")
    private Integer id;
    @TableField(value = "title")
    private String title;
}
