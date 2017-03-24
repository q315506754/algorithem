package com.jiangli.test;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jiangli
 * @date 2017/3/24 11:11
 */
public class CallerTest {
        private long startTs;

        @Before
        public void before() {
            startTs = System.currentTimeMillis();
            System.out.println("----------before-----------");
            System.out.println();
        }

        @After
        public void after() {
            long cost = System.currentTimeMillis() - startTs;
            System.out.println("----------after-----------:cost:"+cost+" ms");
            System.out.println();
        }

        @Test
        public void test_cc() throws Exception{
            StackTraceElement[] tmpElements = Thread.currentThread().getStackTrace();
//            System.out.println(tmpElements[2].getMethodName());
//            System.out.println(tmpElements[2].getClassName());
            for (StackTraceElement tmpElement : tmpElements) {
                System.out.println(BeanUtils.describe(tmpElement));
            }
        }

        @Test
        public void test_cc2() throws Exception{
            StackTraceElement[] tmpElements = (new Exception()).getStackTrace();
//            System.out.println(tmpElements[2].getMethodName());
//            System.out.println(tmpElements[2].getClassName());
            for (StackTraceElement tmpElement : tmpElements) {
                System.out.println(BeanUtils.describe(tmpElement));
            }
        }
}
