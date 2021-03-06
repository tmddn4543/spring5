package com.nautestech.www.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(basePackages = "com.nautestech.www.mapper", sqlSessionFactoryRef="db1SqlSessionFactory")
@EnableTransactionManagement
public class DatabaseConfig {
	@Bean(name = "dbDataSource1")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari.db1")
    public DataSource db1DataSource() {
        return DataSourceBuilder.create().build();
    }
	
	@Bean(name = "db1SqlSessionFactory")
	@Primary
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dbDataSource1")DataSource dataSource) throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis-config.xml"));
		sessionFactory.setMapperLocations(resolver.getResources("classpath:mybatis/mapper/*.xml"));
		return sessionFactory.getObject();
	}
	
	@Bean(name = "db1SqlSessionTemplate")
	@Primary
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
		final SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
		return sqlSessionTemplate;
	}
}
