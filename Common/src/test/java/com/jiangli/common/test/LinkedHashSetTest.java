package com.jiangli.common.test;

import java.util.*;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/17 0017 10:33
 */
public class LinkedHashSetTest {
    public static void main(String[] args) {
        Set<String> set = new LinkedHashSet<>();
        set.add("111");
        set.add("222");
        set.add("111");
        System.out.println(set);
    }

}
