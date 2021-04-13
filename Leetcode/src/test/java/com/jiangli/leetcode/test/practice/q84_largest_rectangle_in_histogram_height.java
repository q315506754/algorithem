package com.jiangli.leetcode.test.practice;

/**
 84. 柱状图中最大的矩形
 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。

 求在该柱状图中，能够勾勒出来的矩形的最大面积。

 *
 分析状态转移方程
1 暴力递归 宽度
2 暴力递归 高度

 [2,1,5,6,2,3]
 10

 [1]
 [0,9]

 *
 * @author Jiangli
 * @date 2021/1/22 11:16
 */
public class q84_largest_rectangle_in_histogram_height {
    public int largestRectangleArea(int[] heights) {
        int max = 0;
        for (int i = 0; i < heights.length ; i++) {
            int left = i-1,right = i+1;
            while (left >= 0 && heights[left] >= heights[i]) {
                left--;
            }
            while (right < heights.length && heights[right] >= heights[i]) {
                right++;
            }
            int width = right - left - 1;
            int height = findMin(heights,left + 1, right - 1);
            int area = width * height;
            max = Math.max(max, area);
        }
        return max;
    }

    private int findMin(int[] heights, int i, int j) {
        int min = Integer.MAX_VALUE;
        for(int n = i;n <= j; n++ ){
            min = Math.min(min,heights[n]);
        }
        return min;
    }
}
