package com.jiangli.leetcode.test.others;

import org.junit.Test;

/**
 * 环长测试
 * 时钟重合
 *
 * @author Jiangli
 * @date 2020/5/9 9:47
 */
public class CycleTest {

    @Test
    public void test_() {
        cycle(1,2,10);
        cycle(1,2,5);

        p();

        cycle(2,3,10);
        cycle(2,3,7);
        cycle(2,3,73);

        p();

        cycle(2,6,10);
        cycle(2,6,7);
        cycle(2,6,73);

        p();

        cycle(1/1200,1,60);
    }

    private void p() {
        System.out.println("------------");
    }

    //p2 > p1
    private void cycle(int p1, int p2, int T) {
        int times = 0;

        int offset = -1;
        do {
            times++;
            offset =  (p2 *times)%T -  (p1 *times)%T;
        } while (offset != 0);

        System.out.println("首次相遇 次数:"+times +" p1坐标:"+(p1 *times)/T +"圈"+ (p1 *times)%T+" " + " p2坐标:"+(p2 *times)/T +"圈"+ (p2 *times)%T+"");
    }



}
