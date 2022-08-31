package com.mybatis.spring.demo.open.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@TableName(value = "open_app")
public class OpenApp {
    @TableField(value = "id")
    private Integer id;
    @TableField(value = "app_name")
    private String appName;
}
