package com.jiangli.spring.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.ResolvableType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class SpringTest implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(SpringTest.class);
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void func() {
        logger.debug(applicationContext.toString());
    }

    @Test
    public void func2() {
        logger.debug(applicationContext.toString());
    }


    static class ABC implements ApplicationContextInitializer<AbstractRefreshableConfigApplicationContext> {
        @Override
        public void initialize(AbstractRefreshableConfigApplicationContext applicationContext) {

        }
    }

    @Test
    public void func3() {
        Class<?> aClass = GenericTypeResolver.resolveTypeArgument(ABC.class, ApplicationContextInitializer.class);
        System.out.println(aClass);
        ResolvableType x = ResolvableType.forClass(ABC.class);
        System.out.println(x);
        System.out.println(x.as(ApplicationContextInitializer.class));
        System.out.println(x.as(ConfigurableApplicationContext.class));
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"classpath*:applicationContext.xml"});
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }

}
