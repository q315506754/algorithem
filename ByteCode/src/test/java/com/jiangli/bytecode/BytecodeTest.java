package com.jiangli.bytecode;

import org.junit.Test;

/**
 * @author Jiangli
 * @date 2019/5/14 20:53
 */
public class BytecodeTest {
    @Test
    public void test_() {
        int n=0;
        n++;
        System.out.println(n);
    }

    @Test
    public void test_2() {
        int n=0;
        n+=3;
        System.out.println(n);
    }

    @Test
    public void test_3() {
        int n=0;
        n+=n;
        System.out.println(n);
    }

    @Test
    public void test_4() {
        int n=3;
        n+=n++;
        System.out.println(n);
    }

    @Test
    public void test_5() {
        String s = "sds";
        s+=",";
        s+="xx";
        System.out.println(s);
    }

    @Test
    public void test_6() {
        String s = "sds";
        s+=",";
        s+=s;
        System.out.println(s);
    }

    @Test
    public void test_7() {
        Object o = new Object();
        String s = o.toString();
        System.out.println(s);
    }



}
