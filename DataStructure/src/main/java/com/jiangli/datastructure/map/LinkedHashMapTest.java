package com.jiangli.datastructure.map;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/14 0014 16:40
 */
public class LinkedHashMapTest {
    public static void main(String[] args) {
        Map<String,String> map = new LinkedHashMap();
        map.put("aaa","aaa");
        map.put("bbb","bbb");
        map.put("ccc","ccc");
        map.put("ddd","ddd");

        boolean ccc = map.values().removeAll(Collections.singleton("ccc"));
        System.out.println(map);

        Collection<String> values = map.values();
        System.out.println(values.getClass());
    }
}
