package com.jiangli.sort.dp;

import java.util.Arrays;

/**
 *
 * 有1,3,5几种分值的硬币，用他们组成n分，要求使用硬币尽可能少
 *
 * http://www.hawstein.com/posts/dp-novice-to-advanced.html
 *
 * @author Jiangli
 *
 *         CreatedTime  2016/7/7 0007 9:56
 */
public class Lv0_Coin {
    static int loopTimes = 0;

    public static void main(String[] args) {
        int N = 50;
        int[] minArr = new int[N+1];
        //int[] coins = new int[]{1,3,5};
        int[] coins = new int[]{5,3,1};
        String[] minDetails = new String[N+1];

        minArr[0] = 0;
        minDetails[0] = "";

        for (int i = 1; i <= N; i++) {
            int min=Integer.MAX_VALUE;
            String detail = "";
            for (int j = 0; j < coins.length; j++) {
                if (i-coins[j] >= 0) {
                    //从大的硬币开始
                    // c[] 为硬币面额
                    //n为总金额
                    //状态转移方程  最小硬币数 ： F(n) = min{ F(n-c[k]) + 1}
                    //边界 F(0) = 0
                    //     k>=0 & k<c.length
                    if (minArr[i-coins[j]]+1 < min) {
                        loopTimes++;
                        min = minArr[i-coins[j]]+1;
                        String s = minDetails[i - coins[j]];
                        detail= s +(s.length()>0?"&":"")+coins[j];
                    }
                }

            }
            minArr[i]=min;
            minDetails[i]=detail;
        }


        System.out.println(Arrays.toString(minArr));
        System.out.println(Arrays.toString(minDetails));
        System.out.println(loopTimes);
    }

}
