package com.jiangli.leetcode.test.practice;

import com.jiangli.junit.spring.RepeatFixedDuration;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * 给定一个整数数组和一个目标值，找出数组中和为目标值的两个数。

 你可以假设每个输入只对应一种答案，且同样的元素不能被重复利用。

 示例:

 给定 nums = [2, 7, 11, 15], target = 9

 因为 nums[0] + nums[1] = 2 + 7 = 9
 所以返回 [0, 1]

 * https://leetcode-cn.com/problems/two-sum/description/
 *
 * @author Jiangli
 * @date 2018/9/18 16:55
 */
public class q1_add_num extends PracticeBase {
    private int[] sample =a(2,7, 3,4,5,6,8,9,10,12,13,14,11,15);
    private Object[] inputAndEx;
    Map<Integer,Integer> map = new HashMap<Integer,Integer>();

    @Before
    public void test_() {
        ats(violent(sample, 9));
        ats(violent(sample, 26));

        for (int i = 0; i < sample.length; i++) {
            map.put(sample[i], i);
        }
    }

    public int[] violent(int[] ar,int target) {
        for (int i = 0; i < ar.length; i++) {
            for (int j = i+1; j < ar.length; j++) {
                if (ar[i]+ar[j] == target) {
                    return new int[]{i,j};
                }
            }
        }
        return null;
    }

    public int[] hashTwoTimes(int[] ar,int target) {
        for (int i = 0; i < ar.length; i++) {
            int key = target - ar[i];
            if (map.containsKey(key)) {
                return new int[]{i,map.get(key)};
            }
        }
        return null;
    }

    @RepeatFixedDuration
    @Test
    public void test_violent() {
        ae(violent(sample,9),a(0,1));
        ae(violent(sample,26),a(9,11));
    }

    @RepeatFixedDuration
    @Test
    public void test_hashTwoTimes() {
        ae(hashTwoTimes(sample,9),a(0,1));
        ae(hashTwoTimes(sample,26),a(9,11));
    }


}
