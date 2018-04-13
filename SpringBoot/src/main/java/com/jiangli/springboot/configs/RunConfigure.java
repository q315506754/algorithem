package com.jiangli.springboot.configs;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * @Author: jiangli
 * @Description: Run后端配置模块
 * @Date: Created in 9:22 2018/1/25
 * @LastModifiedBy:
 */
@Configuration
public class RunConfigure {

    public RunConfigure() {
        System.out.println("RunConfigureRunConfigureRunConfigure");
    }

    /**
     * Run后端数据源
     * @Return
     */
    @Bean
    @ConfigurationProperties("druid.run")
    public DataSource dateSourceRun() {
        return DruidDataSourceBuilder.create().build() ;
    }

    /**
     * Run数据源事务
     * @Return
     */
    @Bean
    public PlatformTransactionManager transactionManagerRun(@Qualifier("dateSourceRun") DataSource dataSource) {
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        return transactionManager;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryRun(@Qualifier("dateSourceRun") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        //开启驼峰映射
        configuration.setMapUnderscoreToCamelCase(true);

        factoryBean.setConfiguration(configuration);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(resolver.getResources("classpath:mapper/run/*.xml"));
        factoryBean.setTypeAliasesPackage("com.zhihuishu.aries.run.model");
        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateRun(@Qualifier("sqlSessionFactoryRun") SqlSessionFactory sessionFactory) throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sessionFactory);
        return template;
    }

}
