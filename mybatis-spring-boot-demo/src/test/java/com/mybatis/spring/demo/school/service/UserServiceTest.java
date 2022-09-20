package com.mybatis.spring.demo.school.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatis.spring.demo.school.entity.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class UserServiceTest {

    @Resource(name = "schoolDataSource")
    javax.sql.DataSource dataSource;

    @Test
    public void contextLoads() throws SQLException {
        System.out.println("当前连接池名称:");
        System.out.println(dataSource.getClass());
        System.out.println("数据库连接实例:"+dataSource.getConnection());
    }
    @Autowired
    UserService userService;

    @Test
    public void testInsert(){
        User user=new User();
        user.setId(1000);
        user.setAge(10);
        user.setName("shenfq");
        user.setEmail("shengfu.qiang@163.com");
        boolean result=userService.save(user);
        System.out.println(result);
    }
    @Test
    @DS("master")
    public void testBatchInsert(){
        List<User> list=new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            User user=new User();
            user.setId(i);
            user.setAge(10);
            user.setName("shenfq");
            user.setEmail("shengfu.qiang@163.com");
            list.add(user);
        }
        boolean result=userService.saveBatch(list);
        System.out.println(result);
    }

    @Test
    public void testPage(){
        IPage page=new Page(1,10);
        page=userService.selectPage(page);
        System.out.println(page.getPages());
        page.getRecords().forEach(System.out::println);
    }

}
