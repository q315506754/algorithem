package com.jiangli.leetcode.test.practice;

/**
 *给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0) 。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水。
 *
 * 说明：你不能倾斜容器。
 *
 分析状态转移方程
1 暴力 双循环 On2
2 双指针
 */
public class q11_container_with_most_water {
    public int maxArea(int[] height) {
        int max = 0;
        for (int i = 0; i < height.length-1; i++) {
            for (int j = i+1; j < height.length; j++) {
                int area = (j-i) * Math.min(height[i],height[j]);
                max = Math.max(max, area);
            }
        }
        return max;
    }
}
