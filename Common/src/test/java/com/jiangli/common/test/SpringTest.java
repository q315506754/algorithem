package com.jiangli.common.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class SpringTest implements ApplicationContextAware{
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static Logger logger = LoggerFactory.getLogger(SpringTest.class);

    @Test
    public void func() {
        System.out.println(applicationContext);
        System.out.println(BeanFactoryUtils.beansOfTypeIncludingAncestors(applicationContext, Object.class, false, false));
    }

    @Test
    public void test_() {
        String[] beanNamesForType = applicationContext.getBeanNamesForType(BeanFactoryPostProcessor.class, true, false);
        System.out.println(Arrays.toString(beanNamesForType));
        System.out.println(Arrays.toString(applicationContext.getBeanNamesForType(BeanPostProcessor.class, true, false)));
    }


}
