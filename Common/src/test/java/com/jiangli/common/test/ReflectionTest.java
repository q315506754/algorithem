package com.jiangli.common.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

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

    class SuperCls {
        public void superA() {
        }
        protected void superB() {
        }
         void superC() {
        }
        private void superD() {
        }
    }
    class ChildCls extends SuperCls{
        public void childA() {
        }
        protected void childB() {
        }
        void childC() {
        }
        private void childD() {
        }
    }

    @Test
    public void test_45() throws Exception {
        //  Object x[] = new String[3];
        //x[0] = new Integer(0);

        //System.out.println(1/0);

        String s = "avb";
        Class<? extends String> aClass = s.getClass();
        System.out.println(s);

        Field value = aClass.getDeclaredField("value");
        value.setAccessible(true);
        Object o = value.get(s);
        System.out.println(o);
        char[] o1 = (char[]) o;
        System.out.println(Arrays.toString(o1));

        o1[1] = 'x';
        o1[2] = 'y';
        System.out.println(Arrays.toString(o1));
        System.out.println(s);

    }

    @Test
    public void func() {
        A a = new A();
        Method[] methods = a.getClass().getMethods();
//        Method[] methods = a.getClass().getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method.getName() + " from " + method.getDeclaringClass());
        }

        for (Method method : SuperCls.class.getDeclaredMethods()) {
            System.out.println(method.getName());
        }

        System.out.println("-------");

        for (Method method : ChildCls.class.getDeclaredMethods()) {
            System.out.println(method.getName());
        }

        System.out.println("---------------------");

        for (Method method : SuperCls.class.getMethods()) {
            System.out.println(method.getName());
        }

        System.out.println("-------");

        for (Method method : ChildCls.class.getMethods()) {
            System.out.println(method.getName());
        }
    }



}
