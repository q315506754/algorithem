package com.jiangli.common.test;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jiangli on 2016/6/14.
 */

public class IteratorTest {

    @Test
    public void func() {
        String[] arr = new String[]{"123","3434"};
        for (String s : arr) {
            System.out.println(s);
        }
    }
    @Test
    public void func2() {
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("3434");
        for (String s : list) {
            System.out.println(s);
        }

//        String[] strArr = new String[2];
        String[] strArr = new String[1];
        String[] strings = list.toArray(strArr);
        System.out.println(strArr == strings);
    }
}
