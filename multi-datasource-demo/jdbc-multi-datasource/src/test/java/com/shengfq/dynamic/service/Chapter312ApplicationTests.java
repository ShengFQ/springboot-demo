package com.shengfq.dynamic.service;

import com.shengfq.dynamic.DemoApplication;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class Chapter312ApplicationTests {

    @Autowired
    protected JdbcTemplate primaryJdbcTemplate;
    @Autowired
    protected JdbcTemplate secondaryJdbcTemplate;

    @Autowired
    private TestService testService;

    @Test
    public void test1() throws Exception {
        // 正确更新的情况
        testService.tx();
        Assertions.assertEquals(30, primaryJdbcTemplate.queryForObject("select age from user where name=?", Integer.class, "aaa"));
        Assertions.assertEquals(30, secondaryJdbcTemplate.queryForObject("select age from user where name=?", Integer.class, "aaa"));
    }

    @Test
    public void test2() throws Exception {
        // 更新失败的情况
        try {
            testService.tx2();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 部分更新失败，test1中的更新应该回滚
            Assertions.assertEquals(30, primaryJdbcTemplate.queryForObject("select age from user where name=?", Integer.class, "aaa"));
            Assertions.assertEquals(30, secondaryJdbcTemplate.queryForObject("select age from user where name=?", Integer.class, "aaa"));
        }
    }

}
