package com.jiangli.practice.test;

import com.jiangli.common.utils.ArrayUtil;
import com.jiangli.practice.eleme.core.OrderDistributor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jiangli
 * @date 2017/4/18 11:11
 */

public class OrderDistributorTest {
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
        OrderDistributor combination = new OrderDistributor(3,new int[]{1,2,3,4,5});
        int count =0;
        for (int[][] ints : combination) {
            System.out.println(ArrayUtil.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }
    @Test
    public void testCommit_3() {
        OrderDistributor combination = new OrderDistributor(2,new int[]{1,2,3,4,5});
        int count =0;
        for (int[][] ints : combination) {
            System.out.println(ArrayUtil.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }
    @Test
    public void testCommit_4() {
        OrderDistributor combination = new OrderDistributor(2,new int[]{1,2,3,4,5,6,7,8});
        int count =0;
        for (int[][] ints : combination) {
            System.out.println(ArrayUtil.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }
    @Test
    public void testCommit_5() {
        OrderDistributor combination = new OrderDistributor(3,new int[]{1,2,3,4,5,6,7,8});
        int count =0;
        for (int[][] ints : combination) {
            System.out.println(ArrayUtil.toString(ints));
            count++;
        }
        System.out.println("total:"+count);
    }
}
