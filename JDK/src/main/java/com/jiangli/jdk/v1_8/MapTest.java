package com.jiangli.jdk.v1_8;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/15 0015 16:45
 */
public class MapTest {

    public static void main(String[] args) {

        Map<Integer, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }

        map.forEach((id, val) -> System.out.println(val));
        map.forEach((Number id,Object val) -> System.out.println(val));

        map.computeIfPresent(3, (num, val) -> val + num);
        map.get(3);             // val33
        map.computeIfPresent(9, (num, val) -> null);
        map.containsKey(9);     // false
        map.computeIfAbsent(23, num -> "val" + num);
        map.containsKey(23);    // true
        map.computeIfAbsent(3, num -> "bam");
        System.out.println(map.get(3));             // val33

        map.remove(3, "val3");
        map.get(3);             // val33
        map.remove(3, "val33");
        map.get(3);             // null

        map.getOrDefault(42, "not found");  // not found

//        map.merge(9, "val9", (value, newValue) -> value.concat(newValue));
        map.merge(9, "val9", (value, newValue) -> null);
        System.out.println(map.get(9));             // val9
        map.merge(9, "concat", (value, newValue) -> value.concat(newValue));
//        map.merge(9, "concat", (value, newValue) -> null);
        System.out.println(map.get(9));                // val9concat
    }

}
