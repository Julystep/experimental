package com.boomshair.shedlocktest.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@MapperScan(basePackages = "com.boomshair.shedlocktest.mapper", sqlSessionFactoryRef = "basedbIesqlSessionFactory", sqlSessionTemplateRef = "basedbIeSqlSessionTemplate")
public class BasedbIeDataSourceConfig {
    @Bean(name = "basedbIeDataSource")
    @ConfigurationProperties(prefix = "shedlock.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "basedbIesqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("basedbIeDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mappers/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "basedbIeTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("basedbIeDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "basedbIeSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("basedbIesqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
