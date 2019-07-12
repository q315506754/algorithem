package com.jiangli.sort.dp;

import java.util.Arrays;

/**
 * 最长非降子序列
 * longest increasing subsequence
 *
 *
 *
 *   5 -> 5
 *   5, 3  ->  3
 *   5, 3, 4 -> 3, 4    [3]+1>0 && 4 > 3
 *   5, 3, 4, 8 -> 3, 4, 8  [3,4]+1 > 1 && 8 > 4
 *   5, 3, 4, 8, 6 -> 3, 4, 6  [3,4]+1 > 1 && 6 > 4
 *   5, 3, 4, 8, 6, 7 -> 3, 4, 6, 7
 *   5, 3, 4, 8, 6, 7 ,1000,20-> 3, 4, 6, 7, 20
 *   5, 3, 4, 8, 6, 7 ,1000,20,23,1,2,1100-> 3, 4, 6, 7, 20,23,1100
 *
 *
 */
public class LvL_LIS {
    public static void main(String[] args) {
        int A[] = {
                5, 3, 4, 8, 6, 7,1000,20,23,1,2,3,1100
        };

        int[][] maxArr = new int[A.length+1][];
        maxArr[0] = new int[0];

        for (int i = 0; i < A.length; i++) {
            int max = 0;//前一个的数组长度
            int idx = -1;

            for (int j = 0; j < i+1; j++) {
                int prevMaxNum=-1;
                if (maxArr[j].length>0) {
                    prevMaxNum=maxArr[j][maxArr[j].length-1];//数组里序列的最大值
                }

                if ( maxArr[j].length + 1 >= max &&  (prevMaxNum <= A[i] ) ) {
                    max =  maxArr[j].length + 1;
                    idx = j;
                }
            }

            // 新的长度不如前一位的长度时  取前一位的lis数据
            if ( maxArr[i].length > maxArr[idx].length + 1 ) {
                maxArr[i+1] = Arrays.copyOf(maxArr[i],maxArr[i].length);
            } else {
                maxArr[i+1] = Arrays.copyOf(maxArr[idx],maxArr[idx].length+1);
                maxArr[i+1][maxArr[i+1].length-1] = A[i];
            }
        }


        int count = -1;
        for (int[] ints : maxArr) {
            ++count;
            System.out.println(count+":"+Arrays.toString(Arrays.copyOf(A, count))+"->"+Arrays.toString(ints));
        }

    }

}
