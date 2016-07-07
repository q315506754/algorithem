package com.jiangli.common.test;

import java.util.*;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/29 0029 16:20
 */
public class HashMapTest {
    public static void main(String[] args) {
        Map<String, Integer> map1 = new HashMap<>();
        map1.put("a", 333);
        map1.put("b", 444);
        Map<String, Integer> map2 = new HashMap<>();
        map2.put("b", 444);
        map2.put("a", 333);
//        map2.put("c", 555);
//        map2.put("d", 666);
        System.out.println(map1.equals(map2));
        System.out.println(map2.equals(map1));

        List<String> list = new ArrayList<>();
        list.add("asb");
        String[] strings = list.toArray(new String[]{"", "22222", "33333", "4444"});
        System.out.println(Arrays.toString(strings));


        map1.put(null, null);
        System.out.println(map1);
        System.out.println(1 << 2);
        System.out.println(8 >> 2);
        System.out.println(8>>>2);

//        int n = 16;//10000
        int n = 1024;//10000
        n |= n >>> 1;// 10000 |= 1000
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        n+=1;
        System.out.println(n);

        int a = 1453;
        int b = 345335;
        System.out.println(a&b);
//        System.out.println(1<<<2);
    }
}
