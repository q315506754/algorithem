package com.jiangli.db.test;

import com.jiangli.common.model.Person;
import com.jiangli.common.model.Student;
import com.jiangli.db.spring.MyFactoryCls;
import com.jiangli.db.spring.TestBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Jiangli on 2016/7/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext-mybatis.xml"})
public class FactoryBeanTest implements ApplicationContextAware{
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    //@Autowired
    //private Student student;

    //@Autowired
    //private HelloFactory helloFactory;

    @Autowired
    private TestBean testBean;

    @Autowired
    //private Student helloFactory;
    private Student student;

    @Autowired
    private Person person;

    @Autowired
    //@Resource
    //private MyFactoryCls myFactoryClsFBean;
    private MyFactoryCls myFactoryCls;


    @Test
    public void func() {
        System.out.println(applicationContext);
        System.out.println(applicationContext.getBean("helloFactory"));
        System.out.println(applicationContext.getBean("&helloFactory"));
        //System.out.println(applicationContext.getBean("*helloFactory"));
        System.out.println(testBean);
        System.out.println(testBean.getClass());
        //System.out.println(helloFactory);
        //System.out.println(helloFactory);
        System.out.println(student);
        //System.out.println(person);
        //System.out.println(myFactoryClsFBean);
        System.out.println(myFactoryCls);
    }
}
