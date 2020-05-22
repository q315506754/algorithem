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
    public void test_22() {
        System.out.println(Math.log(100));
        System.out.println(Math.log10(100));
        System.out.println(Math.log(8)/Math.log(2));
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
