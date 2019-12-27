package com.jiangli.aop.test;

import com.jiangli.aop.spring.AopTestBean;
import com.jiangli.common.core.IHello;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Jiangli on 2016/7/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class SpringAOPTest implements ApplicationContextAware{
    @Autowired
    private IHello iHello;
    //private HelloImpl iHello;

    @Autowired
    private AopTestBean aopTestBean;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void func() {
        System.out.println(applicationContext);
//        System.out.println(Reflection.getCallerClass());
//        System.out.println(iHello.getClass());
//        iHello.sayHello("aaa");
//        String a = "fff"+"xxx";
        String a = "fff";
        a+="xxx";
    }

    @Test
    public void testAopTestBean() {
//        System.out.println(Reflection.getCallerClass());
        System.out.println(iHello.getClass());
        System.out.println(aopTestBean.getClass());
        aopTestBean.func();
        System.out.println("axx");
    }
}
