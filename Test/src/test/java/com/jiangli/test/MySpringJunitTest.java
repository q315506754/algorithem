package com.jiangli.test;

import com.jiangli.junit.spring.RepeatFixedDuration;
import com.jiangli.junit.spring.RepeatFixedTimes;
import com.jiangli.junit.spring.StatisticsJunitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@RunWith(StatisticsJunitRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class MySpringJunitTest implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(MySpringJunitTest.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @RepeatFixedDuration(3000)
    @Test
    public void func() {
        logger.debug(applicationContext.toString());
    }


    @RepeatFixedTimes(1000)
    @Test
    public void func2() {
        logger.debug(applicationContext.toString());
    }

    @Test
    public void func3() {
        logger.debug(applicationContext.toString());
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath*:applicationContext.xml"});
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }

    }

}
