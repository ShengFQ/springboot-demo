package com.mybatis.spring.demo.school.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
@TableName(value = "role")
public class Role {
    @TableField(value = "id")
    private Long id;
    @TableField(value = "role_name")
    private String roleName;
    @TableField(value = "role_describe")
    private String description;
}
