package com.jiangli.concurrent.test;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 * @date 2018/11/12 17:39
 */
public class CopyOnWriteTest {
    @Test
    public void test_() {
        //List<Integer> integers = new ArrayList<Integer>();
        List<Integer> integers = new LinkedList<>();
        integers.add(1);
        for (Integer integer : integers) {
            System.out.println(integer);
            integers.add(3);
        }
    }



}
