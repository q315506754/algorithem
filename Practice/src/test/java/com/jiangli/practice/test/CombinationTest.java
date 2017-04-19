package com.jiangli.practice.test;

import com.jiangli.practice.eleme.core.Combination;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Jiangli
 * @date 2017/4/18 11:11
 */

public class CombinationTest {
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
    public void testCommit() {
        Combination combination = new Combination(3,new int[]{1,2,3,4,5});
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }

    @Test
    public void testCommit_2() {
        Combination combination = new Combination(4,new int[]{1,2,3,4,5,6,7,8});
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }

    @Test
    public void testCommit_3() {
        int LENGTH=20;
        int CHOOSE=10;
        int[] p = new int[LENGTH];
        for (int i = 0; i < LENGTH; i++) {
            p[i]=i+1;
        }

        Combination combination = new Combination(CHOOSE,p);
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }

    @Test
    public void testCommit2() {
        Combination combination = new Combination(1,new int[]{1,2,3,4,5});
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }

    @Test
    public void testCommit3() {
        Combination combination = new Combination(1,new int[]{5});
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }
}
