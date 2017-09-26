package com.jiangli.common.test;

import org.junit.Test;

/**
 * @author Jiangli
 * @date 2017/5/5 11:13
 */
public class BitTest {
    @Test
    public void test_aa() {

        int COUNT_BITS = Integer.SIZE - 3;
        int RUNNING    = -1 << COUNT_BITS;
        int SHUTDOWN   =  0 << COUNT_BITS;
        int STOP       =  1 << COUNT_BITS;
        int TIDYING    =  2 << COUNT_BITS;
        int TERMINATED =  3 << COUNT_BITS;
        int CAPACITY   = (1 << COUNT_BITS) - 1;

        System.out.println(-1==(~1+1));
        System.out.println(-28==(~28+1));
        System.out.println(1==(~-1+1));
        System.out.println(28==(~-28+1));
        print(1);
        print(~1);
        print(~1+1);
        print(-1);
        print(~-1);
         print(RUNNING);
         print(SHUTDOWN);
         print(STOP);
         print(TIDYING);
         print(TERMINATED);
         print(CAPACITY);
         print(~CAPACITY);

    }

    private void print(int x) {
        System.out.println(x+"  "+Integer.toBinaryString(x) +"  "+Integer.toBinaryString(x).length());
    }



}
