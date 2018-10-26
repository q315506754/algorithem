package com.jiangli.sort.dp;

import java.util.Arrays;

/**
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
        int N = 20;
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
