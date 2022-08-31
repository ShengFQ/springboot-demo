package com.mybatis.spring.demo.cms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mybatis.spring.demo.cms.entity.NewsInfo;
import com.mybatis.spring.demo.school.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface NewsInfoMapper extends BaseMapper<NewsInfo> {
    NewsInfo findById(@Param("id") Integer id);

}
