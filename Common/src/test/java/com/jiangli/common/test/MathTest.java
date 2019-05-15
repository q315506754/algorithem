package com.jiangli.common.test;

import org.junit.Test;

/**
 * @author Jiangli
 * @date 2018/5/3 11:26
 */
public class MathTest {
    @Test
    public void test_() {
        //java.lang.ArithmeticException: / by zero
        System.out.println(1 / 0);
    }


    @Test
    public void test_2() {
        System.out.println(Math.round(99.0f));
        System.out.println(Math.round(99.1f));
        System.out.println(Math.round(99.4f));
        System.out.println(Math.round(99.5f));
        System.out.println(Math.round(99.9f));
    }


}
