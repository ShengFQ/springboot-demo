package com.shengfq.pss.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @ClassName：jz_user_jgz
 * @Description：用户照片(警官证)表
 * @Author：shengfq
 * @Date：2022年09月19日 13:34:53
 * @Version
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName( "jz_user_jgz")
public class JzUserJgz  {
    /**
     * id
     */
   /* @TableId(value = "id",type = IdType.AUTO)
    private Long id;
*/
    /**
     * 警员编码（警号）
     */
    @TableId(value = "jycode")
    private String jycode;

    /**
     * 正面路径
     */
    @TableField(value = "zmlj")
    private String zmlj;

    /**
     * 反面路径
     */
    @TableField(value = "fmlj")
    private String fmlj;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private LocalDateTime updateTime;
}
