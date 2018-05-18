package com.jiangli.datastructure.test;

import com.jiangli.junit.spring.StatisticsJunitRunner;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

/**
 * @author Jiangli
 * @date 2018/5/11 10:30
 */
@RunWith(StatisticsJunitRunner.class)
//@RunWith(StatisticsSpringJunitRunner.class)
//@PropertySource({
//        "classpath:test.properties"
//})
//@SpringBootTest
//@ActiveProfiles("test")
public class BaseTest implements EnvironmentAware {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    //-Dspring.profiles.active=test
    @Override
    public void setEnvironment(Environment environment) {
        log.warn("setEnvironment before:{}",environment);
        //StandardEnvironment standardEnvironment = (StandardEnvironment) environment;
        //standardEnvironment.addActiveProfile("test");
        log.warn("setEnvironment after:{}",environment);
    }
}
