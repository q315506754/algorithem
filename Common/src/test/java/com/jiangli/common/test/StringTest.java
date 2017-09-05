package com.jiangli.common.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jiangli
 * @date 2017/7/13 13:38
 */
public class StringTest {
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
        public void test_ads() {
//            String str = "a,b,c,,, ,   ,,";
            String str = "a,b,c,,d,,";
//            String str = "a,b,c,,,,";
            String[] ary = str.split(",");
//预期大于 3，结果是 3
            System.out.println(ary.length);
            System.out.println(Arrays.toString(ary));

            System.out.println("dsa \ud83d\ude02");
        }
        @Test
        public void test_ads22() {
           List<String> a = new ArrayList<String>();
            a.add("3");
            a.add("4");
            a.add("5");
            a.add("1");
            a.add("2");
            for (String temp : a) {
//                if ("1".equals(temp)) {
                if ("2".equals(temp)) {
                    a.remove(temp);
                }
            }
            System.out.println(a);
        }

        @Test
        public void test_aa() {
            final String  lesson_key = String.format("course:lesson:%d:speakerids", null);
            System.out.println(lesson_key);
            System.out.println(String.format("course:lesson:%-30d:speakerids", 2314));

            System.out.println(String.format("course:lesson:%030d:speakerids", 2314));
            System.out.println(String.format("course:lesson:%+20d:speakerids", 2314));
        }


}
