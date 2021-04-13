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
public class q84_largest_rectangle_in_histogram_stack2 {
    public int largestRectangleArea(int[] heights) {
        int max = 0;
        //Deque<Integer> stack = new LinkedList<>();
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < heights.length ; i++) {
            int cur = heights[i];
            if (stack.isEmpty() || heights[stack.peek()] <= cur) {
                stack.push(i);
            } else {
                int right = i;
                do {
                    Integer pop = stack.pop();

                    //opt
                    while (!stack.isEmpty() && heights[pop] == heights[stack.peek()]) {
                        pop = stack.pop();
                    }

                    int left = stack.isEmpty() ? -1 : stack.peek();
                    int width = right - left - 1;
                    int height = heights[pop];
                    int area = width * height;
                    max = Math.max(max, area);
                } while (!stack.isEmpty() && heights[stack.peek()] > cur);
                stack.push(i);
            }
        }

        int right = heights.length;
        while (!stack.isEmpty()) {
            Integer pop = stack.pop();

            //opt
            while (!stack.isEmpty() && heights[pop] == heights[stack.peek()]) {
                pop = stack.pop();
            }

            int left = stack.isEmpty() ? -1 : stack.peek();
            int width = right - left - 1;
            int height = heights[pop];
            int area = width * height;
            max = Math.max(max, area);
        }

        return max;
    }

}
