package com.shengfq.dynamic.service;

import com.shengfq.dynamic.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 通过注解内的标识符号
 * 使用不同的数据源查询不同数据库
 * @author sheng
 * @date 2024-03-31
 * */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    protected JdbcTemplate primaryJdbcTemplate;

    @Autowired
    protected JdbcTemplate secondaryJdbcTemplate;
    @Override
    public List selectAll() {
        return primaryJdbcTemplate.queryForList("select * from user");
    }

    @Override
    public List selectByCondition() {
        return secondaryJdbcTemplate.queryForList("select * from user where age >1");
    }
}
