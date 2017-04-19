package com.jiangli.practice.test;

import com.jiangli.common.utils.ArrayUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Jiangli
 * @date 2017/4/18 11:11
 */

public class ArraySubstractTest {
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
        System.out.println("----------after-----------:cost:" + cost + " ms");
        System.out.println();
    }


    @Test
    public void testCommit_2() {
        int[] ints1 = {1, 2, 3, 4, 5, 6, 7, 8};
        int[] ints2 = { 3,  6, 7};
        int[] ret = ArrayUtil.substractBiSearch(ints1, ints2);
        System.out.println(Arrays.toString(ret));
    }

    @Test
    public void testCommit_3() {
        int[] ints1 = {8,1, 2, 6,3, 4, 5, 7 };
        int[] ints2 = { 6,  3, 7};
        int[] ret = ArrayUtil.substractBiSearch(ints1, ints2);
        System.out.println(Arrays.toString(ret));
    }
    @Test
    public void testCommit_3_1() {
        int[] ints1 = {1, 2, 3};
        int[] ints2 = {1, 2, 3};
        int[] ret = ArrayUtil.substractBiSearch(ints1, ints2);
        System.out.println(Arrays.toString(ret));
    }

    @Test
    public void testCommit_4() {
        int[][] ints1 = {{1, 2, 3}, {4, 5, 6}, {7}, {8}};
        System.out.println(Arrays.toString(ints1));
        System.out.println(ArrayUtil.toString(ints1));
    }

    @Test
    public void testCommit_5() {
        System.out.println(Arrays.toString(ArrayUtil.newArray(30,3)));
        System.out.println(Arrays.toString(ArrayUtil.newArray(30,0)));
    }

}
