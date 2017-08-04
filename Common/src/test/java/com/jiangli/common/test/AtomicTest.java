package com.jiangli.common.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jiangli
 * @date 2017/7/28 17:14
 */
public class AtomicTest {

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
        public void test_sd() {
            Integer i=0;
            func(i);
            System.out.println(i);
        }

    public void func(Integer j) {
        j++;
        j++;
    }

}
