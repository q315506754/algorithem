package com.jiangli.test;

import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

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

    @Test
    public void test_() {
        Map<Long,Object> map = new LinkedHashMap<>();
        map.put(1111L, new Object());
        map.put(1112L, new Object());
//        for (Long aLong : map.keySet()) {
//            map.remove(aLong);
//        }
        Iterator<Map.Entry<Long, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
//        for (Map.Entry<Long, Object> entry : map.entrySet()) {
//
//        }
        System.out.println(map);
    }


}
