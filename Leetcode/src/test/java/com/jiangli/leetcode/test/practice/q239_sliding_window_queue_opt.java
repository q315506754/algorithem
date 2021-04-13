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
 方法二：单调队列
 思路与算法

 我们可以顺着方法一的思路继续进行优化。

 由于我们需要求出的是滑动窗口的最大值，如果当前的滑动窗口中有两个下标 ii 和 jj，其中 ii 在 jj 的左侧（i < ji<j），并且 ii 对应的元素不大于 jj 对应的元素（\textit{nums}[i] \leq \textit{nums}[j]nums[i]≤nums[j]），那么会发生什么呢？

 当滑动窗口向右移动时，只要 ii 还在窗口中，那么 jj 一定也还在窗口中，这是 ii 在 jj 的左侧所保证的。因此，由于 \textit{nums}[j]nums[j] 的存在，\textit{nums}[i]nums[i] 一定不会是滑动窗口中的最大值了，我们可以将 \textit{nums}[i]nums[i] 永久地移除。

 因此我们可以使用一个队列存储所有还没有被移除的下标。在队列中，这些下标按照从小到大的顺序被存储，并且它们在数组 \textit{nums}nums 中对应的值是严格单调递减的。因为如果队列中有两个相邻的下标，它们对应的值相等或者递增，那么令前者为 ii，后者为 jj，就对应了上面所说的情况，即 \textit{nums}[i]nums[i] 会被移除，这就产生了矛盾。

 当滑动窗口向右移动时，我们需要把一个新的元素放入队列中。为了保持队列的性质，我们会不断地将新的元素与队尾的元素相比较，如果前者大于等于后者，那么队尾的元素就可以被永久地移除，我们将其弹出队列。我们需要不断地进行此项操作，直到队列为空或者新的元素小于队尾的元素。

 由于队列中下标对应的元素是严格单调递减的，因此此时队首下标对应的元素就是滑动窗口中的最大值。但与方法一中相同的是，此时的最大值可能在滑动窗口左边界的左侧，并且随着窗口向右移动，它永远不可能出现在滑动窗口中了。因此我们还需要不断从队首弹出元素，直到队首元素在窗口中为止。

 为了可以同时弹出队首和队尾的元素，我们需要使用双端队列。满足这种单调性的双端队列一般称作「单调队列」。

 代码

 作者：LeetCode-Solution
 链接：https://leetcode-cn.com/problems/sliding-window-maximum/solution/hua-dong-chuang-kou-zui-da-zhi-by-leetco-ki6m/
 来源：力扣（LeetCode）
 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

 */
public class q239_sliding_window_queue_opt {
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

            queue.offer(new int[]{newIdx,nums[newIdx]});
            while (queue.peek()[0] < i ) {
                queue.poll();
            }
            ret[i] = queue.peek()[1];
        }
        return ret;
    }
}
