package com.jiangli.leetcode.test.practice;

/**
 *一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
 *
 * 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
 *
 * 问总共有多少条不同的路径？
 *
 输入：m = 3, n = 7
 输出：28
 示例 2：

 输入：m = 3, n = 2
 输出：3
 解释：
 从左上角开始，总共有 3 条路径可以到达右下角。
 1. 向右 -> 向下 -> 向下
 2. 向下 -> 向下 -> 向右
 3. 向下 -> 向右 -> 向下

 *
 分析状态转移方程
 1 dp 1D
 1 dp 2D

 */
public class q62_unique_paths {
    public int uniquePaths(int m, int n) {
        int[][] cache = new int[m][n];

        for (int i = 0; i < n; i++) {
            cache[0][i] = 1;
        }
        for (int i = 0; i < m; i++) {
            cache[i][0] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                cache[i][j] = cache[i][j -1] + cache[i - 1][j];
            }
        }

        return cache[m-1][n-1];
    }
}
