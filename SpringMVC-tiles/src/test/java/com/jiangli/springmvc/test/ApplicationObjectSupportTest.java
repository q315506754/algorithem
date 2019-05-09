package com.jiangli.springmvc.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jiangli
 * @date 2017/5/2 13:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:test/applicationContext.xml"})
public class ApplicationObjectSupportTest extends ApplicationObjectSupport {
    @Test
    public void test_() {
        System.out.println(getApplicationContext());
        System.out.println(getMessageSourceAccessor());
    }

    @Test
    public void test_sdsd() {
        ApplicationContext applicationContext = getApplicationContext();
        BeanDefinitionRegistry ctx = (BeanDefinitionRegistry) applicationContext;
//        ctx.registerBeanDefinition("aa",new RootBeanDefinition(String.class));

        String[] strings = BeanFactoryUtils.beanNamesForTypeIncludingAncestors(getApplicationContext(), Object.class);
        System.out.println(Arrays.toString(strings));

        List<String> collection = new ArrayList<>();
        collection.add("asdsa");
        collection.add("asdsa23");
        collection.add("asdsa234");
        System.out.println( StringUtils.toStringArray(collection));

    }


}
