package com.jiangli.leetcode.test.practice;

import java.util.HashMap;
import java.util.Map;

/**
 斐波那契数，通常用 F(n) 表示，形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：

 F(0) = 0，F(1) = 1
 F(n) = F(n - 1) + F(n - 2)，其中 n > 1
 给你 n ，请计算 F(n) 。

  

 示例 1：

 输入：2
 输出：1
 解释：F(2) = F(1) + F(0) = 1 + 0 = 1

1 暴力递归
 2 递归+缓存
 3 dp
 */
public class q509_fibonacci_cache {
    public int fib(int n) {
        return fibRecur(n,new HashMap<>());
    }
    public int fibRecur(int n, Map<Integer,Integer> cache) {
        if (n <= 1) {
            return n;
        }
        Integer integer = cache.get(n);
        if (integer != null) {
            return integer;
        }
        int ret = fibRecur(n - 1, cache) + fibRecur(n - 2, cache);
        cache.put(n,ret);
        return ret;
    }
}
