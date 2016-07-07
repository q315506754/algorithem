package com.jiangli.sort.dp;

import java.util.Arrays;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/7/7 0007 11:14
 */
public class LIS {
    public static void main(String[] args) {
        int A[] = {
                5, 3, 4, 8, 6, 7
        };

        int[] maxArr = new int[A.length ];

        for (int i = 0; i < A.length; i++) {
            int max = 1;
            for (int j = 0; j < i; j++) {
                if (maxArr[j] + 1 > max &&(A[j] <= A[i] ) ) {
                    max = maxArr[j] + 1;
                }
            }
            maxArr[i] = max;
        }


        System.out.println(Arrays.toString(maxArr));
    }

}
