package com.jiangli.common.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiangli
 * @date 2018/1/10 22:53
 */
public class ForeachTest {
     volatile Object y3 = null;
     static Object y4 = null;
     private Object y5 = null;

    public static void main(String[] args) {
        int count =16;
        System.out.println(16*73/100);
        System.out.println(16*(73/100));
        List<String> list = new ArrayList<String>();
        list.add("aaa");
        list.add("bbb");

        for (String s : list) {

        }

        new ForeachTest().func();

        final int x = 3;
         int x2 = 3;

        final Object y = null;
         Object y2 = null;
    }

    public void func() {

    }
}
