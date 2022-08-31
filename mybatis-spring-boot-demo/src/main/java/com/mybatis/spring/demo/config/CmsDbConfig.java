package com.mybatis.spring.demo.config;

import com.baomidou.mybatisplus.core.MybatisConfiguration;
import com.mybatis.spring.demo.common.CmsConst;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Setter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import javax.sql.DataSource;

/**
 * MyBatis 在 SpringBoot 中动态多数据源配置
 * @author sheng
 * @date 2022-08-02
 * */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.cms")
@MapperScan(basePackages = "com.mybatis.spring.demo.cms.mapper", sqlSessionFactoryRef = "cmsSqlSessionFactory")
@Setter
public class CmsDbConfig {

    @Autowired
    private DBProperties dbProperties;

    // 数据库配置息信息
    private String driverClassName;
    private String url;
    private String username;
    private String password;

    @Bean(name = "cmsDataSource")
    public DataSource unionDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        // 设置数据源信息
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        // 公共属性配置
        dataSource.setMinimumIdle(dbProperties.getMinimumIdle());
        dataSource.setIdleTimeout(dbProperties.getIdleTimeout());
        dataSource.setMaximumPoolSize(dbProperties.getMaximumPoolSize());
        dataSource.setAutoCommit(dbProperties.isAutoCommit());
        dataSource.setPoolName("cms_" + dbProperties.getPoolName());
        dataSource.setMaxLifetime(dbProperties.getMaxLifetime());
        dataSource.setConnectionTimeout(dbProperties.getConnectionTimeout());
        dataSource.setConnectionTestQuery(dbProperties.getConnectionTestQuery());
        return dataSource;
    }

    /**
     * union 事务管理器
     *
     * @return
     */
    @Role(100)
    @Bean(name = CmsConst.TRANSACTION_PRIMARY)
    public DataSourceTransactionManager unionTransactionManager() {
        return new DataSourceTransactionManager(unionDataSource());
    }

    /**
     * unionDataSource Session工厂类
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean(name = "cmsSqlSessionFactory")
    public SqlSessionFactory unionSqlSessionFactory(@Qualifier("cmsDataSource") DataSource dataSource)
            throws Exception {
        MybatisSqlSessionFactoryBean sessionFactory = new MybatisSqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        MybatisConfiguration config = new MybatisConfiguration();
        config.setMapUnderscoreToCamelCase(true);
        sessionFactory.setConfiguration(config);
        //如果不使用xml的方式配置mapper，则可以省去下面这行mapper location的配置。
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/cms/*.xml"));
        return sessionFactory.getObject();
    }
}
