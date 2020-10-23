package com.cy.ares.dao.conf;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration  
public class DruidDBConfig {  
    private Logger logger = LoggerFactory.getLogger(DruidDBConfig.class);  
      
    @Value("${spring.datasource.url}")  
    private String dbUrl;  
      
    @Value("${spring.datasource.username}")  
    private String username;  
      
    @Value("${spring.datasource.password}")  
    private String password;  
      
    @Value("${spring.datasource.driverClassName:com.mysql.jdbc.Driver}")  
    private String driverClassName;  
      
    @Value("${spring.datasource.initialSize:20}")  
    private int initialSize;  
      
    @Value("${spring.datasource.minIdle:20}")  
    private int minIdle;  
      
    @Value("${spring.datasource.maxActive:100}")  
    private int maxActive;  
      
    @Value("${spring.datasource.maxWait:60000}")  
    private int maxWait;  
      
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis:600000}")  
    private int timeBetweenEvictionRunsMillis;  
      
    @Value("${spring.datasource.minEvictableIdleTimeMillis:300000}")  
    private int minEvictableIdleTimeMillis;  
      
    @Value("${spring.datasource.validationQuery:'SELECT 1 FROM DUAL'}")  
    private String validationQuery;  
      
    @Value("${spring.datasource.testWhileIdle:false}")  
    private boolean testWhileIdle;  
      
    @Value("${spring.datasource.testOnBorrow:true}")  
    private boolean testOnBorrow;  
      
    @Value("${spring.datasource.testOnReturn:true}")  
    private boolean testOnReturn;  
      
    @Value("${spring.datasource.poolPreparedStatements:true}")  
    private boolean poolPreparedStatements;  
      
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize:20}")  
    private int maxPoolPreparedStatementPerConnectionSize;  
      
    @Value("${spring.datasource.filters:log4j}")  
    private String filters;  
      
    @Value("{spring.datasource.connectionProperties:}")  
    private String connectionProperties;  
      
    @Bean     //声明其为Bean实例  
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource, 将覆盖其他来源的DataSource 
    public DataSource dataSource(){  
        DruidDataSource datasource = new DruidDataSource();  
          
        datasource.setUrl(this.dbUrl);  
        datasource.setUsername(username);  
        datasource.setPassword(password);  
        datasource.setDriverClassName(driverClassName);
        //configuration  
        datasource.setInitialSize(initialSize);  
        datasource.setMinIdle(minIdle);  
        datasource.setMaxActive(maxActive);  
        datasource.setMaxWait(maxWait);  
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);  
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);  
        datasource.setValidationQuery(validationQuery);  
        datasource.setTestWhileIdle(testWhileIdle);  
        datasource.setTestOnBorrow(testOnBorrow);  
        datasource.setTestOnReturn(testOnReturn);  
        datasource.setPoolPreparedStatements(poolPreparedStatements);  
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);  
        try {  
            datasource.setFilters(filters);  
        } catch (SQLException e) {  
            logger.error("druid configuration initialization filter", e);  
        }  
        datasource.setConnectionProperties(connectionProperties);  
          
        try {
            datasource.init();
        } catch (SQLException e) {
            logger.error("druid init error", e);  
        }
        return datasource;  
    }  
}  
