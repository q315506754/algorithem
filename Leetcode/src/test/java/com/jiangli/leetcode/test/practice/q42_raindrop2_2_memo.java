package com.jiangli.leetcode.test.practice;

import org.junit.Test;

/**
 * 接雨水

暴力解法
 每一个位置的值与左右max的关系
 备忘录优化

   |
   |           |
   |     |     |
|  |  |  |  |  |  |    |

 O n
 O n

 */
//@RunWith(StatisticsJunitRunner.class)
public class q42_raindrop2_2_memo extends PracticeBase {

    class Solution {
        private int[] maxLeftArr;
        private int[] maxRightArr;

        public int trap(int[] height) {
            int sum = 0;

            maxLeftArr = new int[height.length];
            maxRightArr = new int[height.length];
            maxRightInit(0,height);

            for (int i = 0; i < height.length; i++) {
                int min = Math.min(maxLeft(i, height), maxRight(i , height));
                if (min > 0 && min > height[i]) {
                    sum += min - height[i];
                }
            }

            return sum;
        }

        private int maxRightInit(int idx,int[] height) {
            int max=0;
            if (height.length > 0) {
                if (idx < height.length-1) {
                    //max = Math.max(maxRight[idx + 1], height[idx + 1]);
                    max = Math.max(maxRightInit(idx + 1,height), height[idx + 1]);
                }
                maxRightArr[idx] = max;
            }
            return max;
        }

        private int maxRight(int idx,int[] height) {
            return maxRightArr[idx];
        }

        private int maxLeft(int idx,int[] height) {
            int max=0;
            if (height.length > 0) {
                if (idx > 0) {
                    max = Math.max(maxLeftArr[idx - 1], height[idx-1]);
                }
                maxLeftArr[idx] = max;
            }
            return max;
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
        ae(new int[]{}, solution::trap,0);
    }

}
