package com.jiangli.common.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
public class ReflectionTest {
    private static Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    class A {
        public void func() {

        }
    }
    @Test
    public void func() {
        A a = new A();
        Method[] methods = a.getClass().getMethods();
//        Method[] methods = a.getClass().getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName() + " from " + method.getDeclaringClass());
        }
    }



}
