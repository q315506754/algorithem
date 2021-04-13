package com.jiangli.leetcode.test.practice;

import java.util.HashMap;
import java.util.Map;

/**
 * 假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
 *
 * 每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
 *
 * 注意：给定 n 是一个正整数。
 *
 * 示例 1：
 *
 * 输入： 2
 * 输出： 2
 * 解释： 有两种方法可以爬到楼顶。
 * 1.  1 阶 + 1 阶
 * 2.  2 阶
 *
 分析状态转移方程
1 暴力递归
 2 dp

 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/climbing-stairs
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 *
 * @author Jiangli
 * @date 2021/1/22 11:16
 */
public class q70_climbing_stairs_cache {
    public int climbStairs(int n) {
        return climbStairsRecur(n,new HashMap<>());
    }

    public int climbStairsRecur(int n, Map<Integer,Integer> cache) {
        Integer integer = cache.get(n);
        if (integer != null) {
            return integer;
        }
        if (n<=2) {
            cache.put(n, n);
            return n;
        }
        int i = climbStairsRecur(n - 1,cache) + climbStairsRecur(n - 2,cache);
        cache.put(n, i);
        return i;
    }
}
