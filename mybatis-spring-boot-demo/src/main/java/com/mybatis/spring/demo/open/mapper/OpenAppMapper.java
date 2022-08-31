package com.mybatis.spring.demo.open.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mybatis.spring.demo.open.entity.OpenApp;
import com.mybatis.spring.demo.school.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OpenAppMapper extends BaseMapper<OpenApp> {
    @Select("SELECT * FROM open_app WHERE id = #{id}")
    OpenApp findById(@Param("id") Integer id);
}
