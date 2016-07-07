package com.jiangli.sort.dp;

import java.util.Arrays;

/**
 * 平面上有N*M个格子，每个格子中放着一定数量的苹果。
 * 你从左上角的格子开始， 每一步只能向下走或是向右走，每次走到一个格子上就把格子里的苹果收集起来，
 * 这样下去，你最多能收集到多少个苹果。
 *
 * @author Jiangli
 *
 *         CreatedTime  2016/7/7 0007 14:27
 */
public class AppleCollection {
    public static void main(String[] args) {
        int[][] appleBucket = new int[][] {{1,5,2},{4,3,3},{6,2,5},{4,5,2}};
        int[][] appleBucketSum = new int[appleBucket.length][appleBucket[0].length];
        for (int i = 0; i < appleBucket.length; i++) {
            for (int j = 0; j < appleBucket[0].length; j++) {
                int max = i-1 >=0 ?appleBucketSum[i-1][j]:0;
                max = j-1 >=0 ?Math.max(appleBucketSum[i][j-1],max):max;

                max += appleBucket[i][j];
                appleBucketSum[i][j] = max;
            }
        }

        for (int[] ints : appleBucketSum) {
            System.out.println(Arrays.toString(ints));
        }

    }

}
