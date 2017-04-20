package com.jiangli.practice.test;

import com.jiangli.practice.eleme.core.Arrangement;
import com.jiangli.practice.eleme.core.ArrangementSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Jiangli
 * @date 2017/4/18 11:11
 */

public class ArrangementTest {
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
        ArrangementSupport combination = new ArrangementSupport(1,5);
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }

    @Test
    public void testCommit2() {
        ArrangementSupport combination = new ArrangementSupport(2,5);
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }

    @Test
    public void testCommit3() {
        ArrangementSupport combination = new ArrangementSupport(3,3);
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }
    @Test
    public void testCommit4() {
        ArrangementSupport combination = new ArrangementSupport(1,1);
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }
    @Test
    public void testCommit5() {
        ArrangementSupport combination = new ArrangementSupport(0,5);
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }

    @Test
    public void testCommit6() {
        ArrangementSupport combination = new ArrangementSupport(3,1);
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }
    @Test
    public void testCommit7() {
        ArrangementSupport combination = new ArrangementSupport(3,0);
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }
    @Test
    public void testCommit8() {
        ArrangementSupport combination = new ArrangementSupport(5,2);
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }
    @Test
    public void testCommit9() {
        Arrangement combination = new Arrangement(5,new int[]{6,8,9,10,13});
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }
}
