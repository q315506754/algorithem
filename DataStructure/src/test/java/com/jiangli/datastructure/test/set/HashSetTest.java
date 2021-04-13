package com.jiangli.datastructure.test.set;

import org.junit.Test;

import java.util.HashSet;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/14 0014 16:39
 */
public class HashSetTest {
    class O {
         int x = 0;
         String s ;

        @Override
        public int hashCode() {
            return x;
        }

        @Override
        public String toString() {
            return "O{" +
                    "x=" + x +
                    ", s='" + s + '\'' +
                    '}';
        }
    }

    @Test
    public void test_() {
        HashSet<Object> objects = new HashSet<>();
        O o = new O();
        o.s = "hello";
        o.x = 123;
        objects.add(o);
        objects.add(o);
        System.out.println(objects);
        o.x = 124;
        objects.add(o);
        System.out.println(objects);
        objects.add(o);
        System.out.println(objects);
    }


}
