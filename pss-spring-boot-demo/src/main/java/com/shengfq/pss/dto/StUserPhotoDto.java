package com.shengfq.pss.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
* @description 用户照片(警官证)表
* @author shengfq
* @date 2022年09月19日 13:34:53
*/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StUserPhotoDto {
    /**
     * 图片id
     * */
    private Long photoId;
    /**
     * 警员警号
     * */
    private String userId;
    /**
     * 图片类型
     * */
    private String photoType;
    /**
     * 图片名称
     * */
    private String photoName;
    /**
     * 图片路径
     * */
    private String photoPath;
}




