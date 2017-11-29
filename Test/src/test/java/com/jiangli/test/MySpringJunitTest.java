package com.jiangli.test;

import com.jiangli.common.utils.RandomUtil;
import com.jiangli.junit.spring.RepeatFixedDuration;
import com.jiangli.junit.spring.RepeatFixedTimes;
import com.jiangli.junit.spring.StatisticsSpringJunitRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@RunWith(StatisticsSpringJunitRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class MySpringJunitTest implements ApplicationContextAware {

    private static Logger logger = LoggerFactory.getLogger(MySpringJunitTest.class);
    private ApplicationContext applicationContext;
    private A obj;
    private Method aMethod;
    private String str128;
    private String str1024;
    private ArrayList<Object> list100Arr;
    private LinkedList<Object> list100Linked;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @RepeatFixedDuration(3000)
    @Test
    public void func() {
        logger.debug(applicationContext.toString());
    }

    @RepeatFixedDuration()
    @Test
    public void funcEmptry() {
    }

    public static void staticEmpty() {
    }

    @RepeatFixedDuration()
    @Test
    public void funcInvokeEmptry() {
        funcEmptry();
    }

    @RepeatFixedDuration()
    @Test
    public void funcInvokeStaticEmptry() {
        staticEmpty();
    }

    @RepeatFixedDuration()
    @Test
    public void func_print() {
        System.out.println("hello");
    }

    @RepeatFixedDuration()
    @Test
    public void func_printstr128() {
        System.out.println(str128);
    }

    @RepeatFixedDuration()
    @Test
    public void func_printstr1024() {
        System.out.println(str1024);
    }

    @RepeatFixedDuration()
    @Test
    public void func_listiterarr_l_1() {
        for (Object o : list100Arr) {

        }
    }
    @RepeatFixedDuration()
    @Test
    public void func_stream_iter() {
        list100Arr.stream().forEach(o -> {

        });
    }
    @RepeatFixedDuration()
    @Test
    public void func_stream_iter3() {
        list100Arr.parallelStream().forEach(o -> {

        });
    }

    @RepeatFixedDuration()
    @Test
    public void func_stream_iter2() {
        list100Linked.stream().forEach(o -> {

        });
    }
    @RepeatFixedDuration()
    @Test
    public void func_stream_iter4() {
        list100Linked.parallelStream().forEach(o -> {

        });
    }

    @RepeatFixedDuration()
    @Test
    public void func_listiterarr_l_2() {
        for (Object o : list100Arr) {
            for (Object o2 : list100Arr) {

            }
        }
    }
    @RepeatFixedDuration()
    @Test
    public void func_listiterlin_l_1() {
        for (Object o : list100Linked) {

        }
    }
    @RepeatFixedDuration()
    @Test
    public void func_listiterlin_l_2() {
        for (Object o : list100Linked) {
            for (Object o2 : list100Linked) {

            }
        }
    }

    class A {
        int a;
        int b;
        int c;
        int d;
        int e;

        public int getA() {
            return a;
        }

        public A setA(int a) {
            this.a = a;
            return this;
        }

        public int getB() {
            return b;
        }

        public A setB(int b) {
            this.b = b;
            return this;
        }

        public int getC() {
            return c;
        }

        public A setC(int c) {
            this.c = c;
            return this;
        }

        public int getD() {
            return d;
        }

        public A setD(int d) {
            this.d = d;
            return this;
        }

        public int getE() {
            return e;
        }

        public A setE(int e) {
            this.e = e;
            return this;
        }
    }

    @Before
    public void bf() {
        obj = new A();
        try {
            aMethod = obj.getClass().getDeclaredMethod("getA", new Class[]{});
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        str128 = RandomUtil.getRandomString(128);
        str1024 = RandomUtil.getRandomString(1024);
        list100Arr = new ArrayList<>();
        list100Linked = new LinkedList<>();
        int i = 100;
        while (i--> 0 ) {
            list100Arr.add(new Object());
            list100Linked.add(new Object());
        }
    }


    @RepeatFixedDuration(6000)
    @Test
    public void testget() {
//        logger.debug(applicationContext.toString());
        int a = obj.getA();
        System.out.println(a);
    }

    @RepeatFixedDuration
    @Test
    public void testget2() {
//        logger.debug(applicationContext.toString());
        obj.getA();
    }

    @RepeatFixedDuration
    @Test
    public void testnew() {
//        logger.debug(applicationContext.toString());
        new Object();
    }
    @RepeatFixedDuration
    @Test
    public void testnew2() {
//        logger.debug(applicationContext.toString());
        new A();
    }

    @RepeatFixedDuration(6000)
    @Test
    public void testreflection() {
//        logger.debug(applicationContext.toString());
        try {
            Object invoke = aMethod.invoke(obj);
            System.out.println(invoke);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RepeatFixedDuration()
    @Test
    public void testreflection2() {
        try {
            aMethod.invoke(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
