package com.mybatis.spring.demo.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatis.spring.demo.cms.entity.NewsInfo;
import com.mybatis.spring.demo.cms.mapper.NewsInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class NewsInfoService extends ServiceImpl<NewsInfoMapper, NewsInfo> implements IService<NewsInfo> {
    private final int BATCH_SIZE=10000;
    @Autowired
    NewsInfoMapper newsInfoMapper;
    public IPage<NewsInfo> selectPage(IPage page){
        //AbstractWrapper wrapper= Wrappers.<User>lambdaQuery().ge(User::getAge, 1).orderByAsc(User::getAge);
        return this.newsInfoMapper.selectPage(page,null);
    }

    public NewsInfo getById(Integer id){
       return this.newsInfoMapper.findById(id);
    }
}
