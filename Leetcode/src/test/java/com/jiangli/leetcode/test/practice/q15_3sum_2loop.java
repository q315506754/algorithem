package com.jiangli.leetcode.test.practice;

import java.util.*;

/**
 给你一个包含 n 个整数的数组 nums，判断 nums 中是否存在三个元素 a，b，c ，使得 a + b + c = 0 ？请你找出所有和为 0 且不重复的三元组。

 注意：答案中不可以包含重复的三元组。

  

 示例 1：

 输入：nums = [-1,0,1,2,-1,-4]
 输出：[[-1,-1,2],[-1,0,1]]

 *
 分析状态转移方程
1 暴力 3循环 On3
2 暴力 双循环 On2
2 3指针
 */
public class q15_3sum_2loop {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> ret = new ArrayList<>();
        Map<Integer,Integer> map = new HashMap<Integer,Integer>();

        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int i = 0; i < nums.length-1; i++) {
            for (int j = i+1; j < nums.length; j++) {
                int target = -(nums[i] + nums[j]);
                Integer idx = map.get(target);
                if (idx !=null && idx != i && idx != j) {
                    List<Integer> asList = Arrays.asList(nums[i], nums[j], target);
                    Collections.sort(asList);
                    addNoDup(ret, asList);
                }
            }
        }
        return ret;
    }

    private  void addNoDup(List<List<Integer>> ret, List<Integer> asList) {
        for (List<Integer> list : ret) {
            if (Arrays.equals(list.toArray(), asList.toArray())) {
                return;
            }
        }

        ret.add(asList);
    }
}
