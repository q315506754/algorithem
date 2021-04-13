package com.jiangli.leetcode.test.practice;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 84. 柱状图中最大的矩形
 给定 n 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1 。

 求在该柱状图中，能够勾勒出来的矩形的最大面积。

 *
 分析状态转移方程
1 暴力递归 宽度
2 暴力递归 高度
 3 单调栈

 [2,1,5,6,2,3]
 10

 [1]
 [0,9]

 *
 * @author Jiangli
 * @date 2021/1/22 11:16
 */
public class q84_largest_rectangle_in_histogram_stack2_sentinel {
    public int largestRectangleArea(int[] heights) {
        int max = 0;
        //Deque<Integer> stack = new LinkedList<>();
        //Deque<Integer> stack = new LinkedList<>();

        int[] tHeights = new int[heights.length + 2];
        System.arraycopy(heights,0,tHeights,1,heights.length);
        tHeights[0] = 0;
        tHeights[tHeights.length-1] = 0;

        Deque<Integer> stack = new ArrayDeque<>(tHeights.length);
        stack.push(0);
        for (int i = 0; i < tHeights.length ; i++) {
            int cur = tHeights[i];

            while (tHeights[stack.peek()] > cur) {
                int right = i;

                Integer pop = stack.pop();

                //opt
                while (tHeights[pop] == tHeights[stack.peek()]) {
                    pop = stack.pop();
                }

                int left = stack.isEmpty() ? -1 : stack.peek();
                int width = right - left - 1;
                int height = tHeights[pop];
                int area = width * height;
                max = Math.max(max, area);
            }

            stack.push(i);
        }

        return max;
    }

}
