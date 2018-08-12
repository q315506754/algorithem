package com.jiangli.common.test;

import org.junit.Test;

/**
 * @author Jiangli
 * @date 2018/6/27 16:49
 */
public class HashTest {
    @Test
    public void test_() {
        Object o = new Object();
        System.out.println(System.identityHashCode(o));
        System.out.println(o.hashCode());
    }



}
