package com.jiangli.leetcode.test.practice;

import java.util.PriorityQueue;

/**
 *
 239. 滑动窗口最大值
 给你一个整数数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧
 。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。

 返回滑动窗口中的最大值。
 示例 1：

 输入：nums = [1,3,-1,-3,5,3,6,7], k = 3
 输出：[3,3,5,5,6,7]
 解释：
 滑动窗口的位置                最大值
 ---------------               -----
 [1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
 示例 2：

 输入：nums = [1], k = 1
 输出：[1]

 1  暴力法 O(nk)
 2 优先队列 O(nlogk)
 3 双端队列


 */
public class q239_sliding_window_queue {
    public int[] maxSlidingWindow(int[] nums, int k) {
        //idx, val
        PriorityQueue<int[]> queue = new PriorityQueue<>((o1, o2) -> {
            return Integer.valueOf(o2[1]).compareTo(Integer.valueOf(o1[1]));
        });

        int[] ret = new int[nums.length - k + 1];
        for (int i = 0; i < k; i++) {
            queue.offer(new int[]{i,nums[i]});
        }
        ret[0] = queue.peek()[1];

        for (int i = 1; i < nums.length - k + 1; i++) {
            int newIdx = i + k - 1;
            if (nums[newIdx] >= queue.peek()[1]) {
                queue.clear();
                queue.offer(new int[]{newIdx,nums[newIdx]});
            } else {
                queue.offer(new int[]{newIdx,nums[newIdx]});
                while (queue.peek()[0] < i ) {
                    queue.poll();
                }
            }

            ret[i] = queue.peek()[1];
        }
        return ret;
    }
}
