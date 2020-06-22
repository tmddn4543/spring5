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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@MapperScan(basePackages = "com.nautestech.www.mapper2", sqlSessionFactoryRef="db2SqlSessionFactory")
@EnableTransactionManagement
public class DatabaseConfig2 {
	@Bean(name = "dbDataSource2")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.db2")
    public DataSource db2DataSource() {
        return DataSourceBuilder.create().build();
    }
	
	@Bean(name ="db2SqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(@Qualifier("dbDataSource2")DataSource dataSource) throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(dataSource);
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		sessionFactory.setConfigLocation(resolver.getResource("classpath:mybatis/mybatis-config.xml"));
		sessionFactory.setMapperLocations(resolver.getResources("classpath:mybatis/mapper2/*.xml"));
		return sessionFactory.getObject();
	}
	
	@Bean(name ="db2SqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) throws Exception {
		final SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
		return sqlSessionTemplate;
	}
}
