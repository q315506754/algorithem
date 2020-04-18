package com.jiangli.leetcode.test.practice;

import org.junit.Test;

/**
 * 接雨水

 暴力解法
 每一个位置的值与左右max的关系

 |
 |           |
 |     |     |
 |  |  |  |  |  |  |    |

 */
//@RunWith(StatisticsJunitRunner.class)
public class q42_raindrop2_1_baoli extends PracticeBase {

    class Solution {
        public int trap(int[] height) {
            int sum = 0;

            for (int i = 0; i < height.length; i++) {
                int min = Math.min(max(0, i - 1, height), max(i + 1, height.length-1, height));
                if (min > 0 && min > height[i]) {
                    sum += min - height[i];
                }
            }

            return sum;
        }

        private int max(int from, int to, int[] height) {
            if (from <=to && to < height.length) {
                int max = height[from];

                for (int i = from; i <= to; i++) {
                    if (height[i] > max ) {
                        max = height[i];
                    }
                }
                return max;
            }

            return 0;
        }
    }

    //@RepeatFixedDuration
    @Test
    public void test_() {
        //shouldPrint(false);
        Solution solution = new Solution();
        ae(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}, solution::trap,6);
        ae(new int[]{5,4,1,2}, solution::trap,1);
        ae(new int[]{0,1,0,0}, solution::trap,0);
        ae(new int[]{0}, solution::trap,0);
        ae(new int[]{1}, solution::trap,0);
        ae(new int[]{1,2}, solution::trap,0);
        ae(new int[]{0,2,0}, solution::trap,0);
    }

}
