package com.jiangli.sort.dp;

import java.util.Arrays;

/**
 * 最长非降子序列长度
 * longest increasing subsequence
 *
 *   5, 3, 4, 8, 6, 7 -> 3, 4, 6, 7
 *   5, 3, 4, 8, 6 -> 3, 4, 6
 *
 *
 * @author Jiangli
 *
 *         CreatedTime  2016/7/7 0007 11:14
 */
public class LvL_LIS_length {
    public static void main(String[] args) {
        int A[] = {
                5, 3, 4, 8, 6, 7,1000,20,23,1,2,1100
        };

        //1 1 2 3 3 4
        //0 0 2 3 3 5

        int[] maxArr = new int[A.length];
        int[] maxNumIdxArr = new int[A.length];

        for (int i = 0; i < A.length; i++) {
            int max = 1;
            int idx = 0;
            boolean existGte = false;

            for (int j = 0; j < i; j++) {
                if ( maxArr[j] + 1 >= max &&  (A[j] <= A[i] ) ) {
                    max = maxArr[j] + 1;
                    idx = j;
                    existGte = true;
                }
            }

            //取前一个的
            if (!existGte && i>0) {
                max = maxArr[i-1];
                idx = maxNumIdxArr[i-1];
            }

            maxArr[i] = max;
            maxNumIdxArr[i] = idx;
        }


        System.out.println(Arrays.toString(maxArr));
        System.out.println(Arrays.toString(maxNumIdxArr));
    }

}
