package com.mybatis.spring.demo.open.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatis.spring.demo.open.entity.OpenApp;
import com.mybatis.spring.demo.open.mapper.OpenAppMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class OpenAppService extends ServiceImpl<OpenAppMapper, OpenApp> implements IService<OpenApp> {
    private final int BATCH_SIZE=10000;

    public IPage<OpenApp> selectPage(IPage page){
        //AbstractWrapper wrapper= Wrappers.<User>lambdaQuery().ge(User::getAge, 1).orderByAsc(User::getAge);
        return this.getBaseMapper().selectPage(page,null);
    }

    public OpenApp getById(Integer id){
      return  this.getBaseMapper().findById(id);
    }
}
