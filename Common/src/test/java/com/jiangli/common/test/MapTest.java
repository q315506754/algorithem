package com.jiangli.common.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Jiangli
 * @date 2017/7/12 19:16
 */
public class MapTest {

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
        public void test_() {
//            Map<String, Integer> mp = new HashMap<>();
            Map<String, Integer> mp = new LinkedHashMap<>();
            mp.put("aaa",1);
            mp.put("bbb",2);
            mp.put("ccc",3);
            mp.put("ddd",null);
            mp.put("eeee",null);
            System.out.println(mp);
            System.out.println(mp.keySet());
            System.out.println(mp.values());
        }

        @Test
        public void test_generic() {
    //            Map<String, Integer> mp = new HashMap<>();
            Map<? super String, ? extends Number> mp = new LinkedHashMap<String,Integer>();
            Number eeee = mp.get("eeee");
            //mp.put(new Object(),null); //error
            mp.put(new String(),null);
            Set<? super String> objects = mp.keySet();
            for (Object object : objects) {

            }
            Collection<? extends Number> values = mp.values();
            for (Number value : values) {

            }
            System.out.println(mp);
            System.out.println(mp.keySet());
            System.out.println(mp.values());
        }


}
