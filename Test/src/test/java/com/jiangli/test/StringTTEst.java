package com.jiangli.test;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Administrator
 *
 *         CreatedTime  2016/9/1 0001 16:38
 */
public class StringTTEst {

    @Test
    public void func() {
        String s="a"+"b"+"c"+"d";

        StringBuilder sb = new StringBuilder();
        String a = "a";
        String b = "b";
        String c = "c";
        String d = "d";
        String s2 = a+b+c+d;
        Assert.assertTrue(true);
    }

}
