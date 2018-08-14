package com.jiangli.spring.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

/**
 * @author Jiangli
 * @date 2018/7/27 8:59
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class ClassPathBeanDefinitionScannerTest implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void func() {
        System.out.println(applicationContext);
        System.out.println(Arrays.toString(applicationContext.getBeanDefinitionNames()));
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner((GenericApplicationContext)applicationContext);
        scanner.scan("com.jiangli");
        System.out.println(Arrays.toString(applicationContext.getBeanDefinitionNames()));
    }

    public static void main(String[] args) {

    }

}
