package com.shengfq.pss.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shengfq.pss.dto.StUserPhotoDto;
import com.shengfq.pss.entity.JzUserJgz;
import com.shengfq.pss.mapper.JzUserJgzMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


/**
* @description 用户照片(警官证)表
* @author shengfq
* @date 2022年09月19日 13:34:53
*/
@Service
public class JzUserJgzService extends ServiceImpl<JzUserJgzMapper, JzUserJgz>  {
    @Autowired
    JzUserJgzMapper jzUserJgzMapper;
    /**
     * 将外部表记录转换后写入目标表
     * */
    public int addOrUpdate(StUserPhotoDto stUserPhotoDto)throws Exception{
        /*JzUserJgz entity=new JzUserJgz();
        entity.setJycode(stUserPhotoDto.getUserId());
        if(stUserPhotoDto.getPhotoType()=="1"){
            entity.setFmlj(stUserPhotoDto.getPhotoPath());
        }else{
            entity.setZmlj(stUserPhotoDto.getPhotoPath());
        }*/
        JzUserJgz entity=UserPhotoAssembler.assembleEntity(stUserPhotoDto);
        boolean success= this.saveOrUpdate(entity);
        if(success){
            return 1;
        }
        return 0;
    }
    /**
     * 一个警号对应一条记录
     * */
    private int saveOrUpdateEntity(JzUserJgz entity) throws Exception{
        int result=0;
        if(StrUtil.isBlankIfStr(entity.getJycode())){
            throw new IllegalArgumentException("警员编号不能为空");
        }
        JzUserJgz data=getByUserId(entity.getJycode());
        if(Optional.ofNullable(data).isPresent()){
            //更新
          //  entity.setUserId(data.getUserId());
            entity.setUpdateTime(LocalDateTime.now());
            return this.jzUserJgzMapper.updateById(entity);
        }else{
            //插入
            entity.setCreateTime(LocalDateTime.now());
            result= this.jzUserJgzMapper.insert(entity);
        }
        return result;
    }
    /**
     *根据警号查找照片记录
     * 一个警号对应一条记录
     * */
    private JzUserJgz getByUserId(String jyCode) throws Exception{
        if(StrUtil.isBlankIfStr(jyCode)){
            throw new IllegalArgumentException("警员编号不能为空");
        }
        JzUserJgz result=new JzUserJgz();
        QueryWrapper<JzUserJgz> wrapper=new QueryWrapper(JzUserJgz.class);
        wrapper.eq("jycode",jyCode);
       List<JzUserJgz> list= this.jzUserJgzMapper.selectList(wrapper);
        if(list!=null && list.size()>0){
            result= list.get(0);
        }
        return result;
    }
}




