package com.mybatis.spring.demo.school.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mybatis.spring.demo.school.entity.User;
import com.mybatis.spring.demo.school.mapper.UserMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService extends ServiceImpl<UserMapper,User> implements IService<User> {
    private final int BATCH_SIZE=10000,expireTime=300;
    public IPage<User> selectPage(IPage page){
        //AbstractWrapper wrapper= Wrappers.<User>lambdaQuery().ge(User::getAge, 1).orderByAsc(User::getAge);
        return this.getBaseMapper().selectPage(page,null);
    }
}
