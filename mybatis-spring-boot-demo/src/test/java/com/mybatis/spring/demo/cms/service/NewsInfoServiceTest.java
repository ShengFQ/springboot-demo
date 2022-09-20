package com.mybatis.spring.demo.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatis.spring.demo.cms.entity.NewsInfo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.SQLException;

@SpringBootTest
public class NewsInfoServiceTest {

    @Resource(name = "cmsDataSource")
    javax.sql.DataSource dataSource;
    @Test
    public void contextLoads() throws SQLException {
        System.out.println("当前连接池名称:");
        System.out.println(dataSource.getClass());
        System.out.println("数据库连接实例:"+dataSource.getConnection());
    }
    @Autowired
    NewsInfoService service;

    @Test
    public void testGet(){
        NewsInfo newsInfo=service.getById(1);
        System.out.println(newsInfo);
    }

    @Test
    public void testPage(){
        IPage page=new Page(1,10);
        page=service.selectPage(page);
        System.out.println(page.getPages());
        page.getRecords().forEach(System.out::println);
    }
}
