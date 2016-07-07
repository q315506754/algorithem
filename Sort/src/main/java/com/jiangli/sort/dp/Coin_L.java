package com.jiangli.sort.dp;

import java.util.Arrays;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/7/7 0007 9:56
 */
public class Coin_L {

    public static void main(String[] args) {
        int N = 20;
        int[] minArr = new int[N];
        int[] coins = new int[]{1,3,5};
        String[] minDetails = new String[N+1];

        minArr[0] = 0;
        minDetails[0] = "";

        for (int i = 0; i < N; i++) {
            int min=Integer.MAX_VALUE;
            String detail = "";
            for (int j = 0; j < coins.length; j++) {
                if (i-coins[j] >= 0) {
                    if (minArr[i-coins[j]]+1 < min) {
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
    }

}
