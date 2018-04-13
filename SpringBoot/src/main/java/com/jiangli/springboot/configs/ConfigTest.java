package com.jiangli.springboot.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})
@EnableConfigurationProperties(DbDto2.class)
@PropertySource({
        "classpath:temp/jdbc.properties"
        ,"classpath:temp/mysql.properties"
})
public class ConfigTest {

    public static void main(String[] args) {
        SpringApplication.run(ConfigTest.class, args);
    }


    /**
     * Run后端数据源
     * @Return
     */
    @Bean
    @ConfigurationProperties("druid.run")
    public DbDto dbDto() {
        return new DbDto();
    }

    @Autowired
    private DataSource dateSourceRun;

    @Autowired
    private DbDto dbDto;

    @Autowired
    private DbDto2 dbDto2;

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println(dateSourceRun);
            System.out.println(dateSourceRun.getClass());
            //System.out.println(BeanUtils.describe(dateSourceRun));
            System.out.println(dbDto);
            System.out.println(dbDto2);
        };
    }

}