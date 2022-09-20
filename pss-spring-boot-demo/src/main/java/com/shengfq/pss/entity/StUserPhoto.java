package com.shengfq.pss.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

/**
 * @ClassName：st_user_photo
 * @Description：用户照片(警官证)表
 * @Author：shengfq
 * @Date：2022年09月19日 13:34:53
 * @Version
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("st_user_photo")
public class StUserPhoto {
    /**
     * 照片ID
     */
    @TableId(value = "PHOTO_ID",type = IdType.AUTO)
    private Long photoId;

    /**
     * 用户的警号
     */
    @Column(name = "USER_ID")
    private String userId;

    /**
     * 照片类型（0正面，1反面）
     */
    @Column(name = "PHOTO_TYPE")
    private String photoType;

    /**
     * 照片名称
     */
    @Column(name = "PHOTO_NAME")
    private String photoName;

    /**
     * 是否删除（0否,1是）
     */
    @Column(name = "DEL_FLAG")
    private String delFlag;

    /**
     * 创建人
     */
    @Column(name = "CREATOR")
    private String creator;

    /**
     * 创建时间
     */
    @Column(name = "CREATETIME")
    private LocalDateTime createtime;

    /**
     * 修改人
     */
    @Column(name = "UPDATOR")
    private String updator;

    /**
     * 修改时间
     */
    @Column(name = "UPDATETIME")
    private LocalDateTime updatetime;

    /**
     * 照片内容
     */
    @Column(name = "PHOTO")
    private byte[] photo;
}
