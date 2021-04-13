package com.jiangli.leetcode.test.practice;

/**
 一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为“Start” ）。

 机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为“Finish”）。

 现在考虑网格中有障碍物。那么从左上角到右下角将会有多少条不同的路径？



 网格中的障碍物和空位置分别用 1 和 0 来表示。

  

 示例 1：


 输入：obstacleGrid = [[0,0,0],[0,1,0],[0,0,0]]
 输出：2
 解释：
 3x3 网格的正中间有一个障碍物。
 从左上角到右下角一共有 2 条不同的路径：
 1. 向右 -> 向右 -> 向下 -> 向下
 2. 向下 -> 向下 -> 向右 -> 向右
 示例 2：


 输入：obstacleGrid = [[0,1],[0,0]]
 输出：1
 *
 分析状态转移方程
 1 dp 1D
 1 dp 2D

 */
public class q62_unique_pathsII_dp2 {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;

        int[]cache = new int[n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {

                if (obstacleGrid[i][j] == 1) {
                    cache[j] = 0;
                } else {
                    if (i == 0 && j == 0) {
                        cache[j] = 1;
                        continue;
                    }

                    int left = 0;
                    if (j > 0) {
                        left  = cache [j-1];
                    }
                    cache[j] = left + cache[j];
                }
            }
        }
        return cache[n-1];
    }
}
