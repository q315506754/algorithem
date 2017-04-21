package com.jiangli.practice.test;

import com.jiangli.practice.eleme.core.ArrangementSupport;
import com.jiangli.practice.eleme.core.LimittedArrangementSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * @author Jiangli
 * @date 2017/4/18 11:11
 */

public class LimittedArrangementTest {
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
        //C3 2 * A3 2

        LimittedArrangementSupport combination = new LimittedArrangementSupport(3,3,2);
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }

    @Test
    public void testCommit21() {
        //C3 2 * A3 2

        LimittedArrangementSupport combination = new LimittedArrangementSupport(3,1,2);
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }

    @Test
    public void testCommit22() {
        //C3 2 * A3 2

        LimittedArrangementSupport combination = new LimittedArrangementSupport(3,0,2);
        int count =0;
        for (int[] ints : combination) {
            System.out.println(Arrays.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }
}
