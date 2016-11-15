package com.jiangli.common.utils;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jiangli
 * @date 2016/11/15 8:59
 */
public class StringHelperTest {
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
    public void test_asdas() {
//        String s = "aBCDEFG";
        String s = "getGuguday";
        String s1 = StringHelper.camelToUnderscore(s);
        String s2 = StringHelper.camelToUnderscore(s,false);
        System.out.println(s1);
        System.out.println(s2);
        System.out.println(StringHelper.underscoreToCamel(s1));
    }
}