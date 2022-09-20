package com.shengfq.pss.service;

import com.shengfq.pss.dto.StUserPhotoDto;
import com.shengfq.pss.entity.JzUserJgz;

/**
 * ClassName: UserPhotoAssembler
 * Description:
 *
 * @author shengfq
 * @date: 2022/9/19 2:54 下午
 */
public class UserPhotoAssembler {
    /**
     * source table dto assemble target table dto
     * 根据正反面描述,将图片url写到不同字段
     * */
    public static JzUserJgz assembleEntity(StUserPhotoDto entity){
        JzUserJgz dto=new JzUserJgz();
        dto.setJycode(entity.getUserId());
        if("0".equals(entity.getPhotoType())){
            //正面照片路径存储
            dto.setZmlj(entity.getPhotoPath());
        }else if("1".equals(entity.getPhotoType())){
            //反面照片路径存储
            dto.setFmlj(entity.getPhotoPath());
        }
        return dto;
    }


}
