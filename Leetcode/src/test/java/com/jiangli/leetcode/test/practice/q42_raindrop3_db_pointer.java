package com.jiangli.leetcode.test.practice;

import org.junit.Test;

/**
 * 接雨水

左右双指针版


   |
   |           |
   |     |     |
|  |  |  |  |  |  |    |

 O n
 O n

 总结：看指针位置的值与左右max的关系
 左右max最小的那个决定了值

 */
//@RunWith(StatisticsJunitRunner.class)
public class q42_raindrop3_db_pointer extends PracticeBase {

    class Solution {

        public int trap(int[] height) {
            int sum = 0;

            int left = 1,right = height.length-2;
            int l_max = 0;
            int r_max = 0;

            while (left <= right) {
                l_max = Math.max(l_max,height[left-1]);
                r_max = Math.max(r_max,height[right+1]);

                if (l_max < r_max) {
                    if (l_max > height[left]) {
                        sum+= l_max - height[left];
                    }
                    left++;
                } else {
                    if (r_max > height[right]) {
                        sum+= r_max - height[right];
                    }
                    right--;
                }
            }


            return sum;
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
