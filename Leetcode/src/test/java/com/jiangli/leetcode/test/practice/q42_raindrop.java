package com.jiangli.leetcode.test.practice;

import org.junit.Test;

/**
 * 接雨水

 找到最高的两个[A,B]，区间值加入sum
 算出区间端点[L,R]
 从右边第一个开始，直到B，找到最高的值B2，计算[B,B2]区间值加入sum,B=B2,重复直到B=R
 从左边第一个开始，直到A，找到最高的值A2，计算[A2,A]区间值加入sum,A=A2,重复直到A=L

   |
   |           |
   |     |     |
|  |  |  |  |  |  |    |

 */
//@RunWith(StatisticsJunitRunner.class)
public class q42_raindrop extends PracticeBase {

    class Solution {
        public int calcSum(int[] height,int from,int to) {
            int sum =  (height[from] < height[to] ? height[from] : height[to]) * (to - from - 1);

            for (int i = from + 1; i<to;i++) {
                sum -= height[i];
            }

            return sum;
        }

        public int trap(int[] height) {
            int sum = 0;

            if (height.length > 2) {
                int L=0,R=height.length-1,i=L,j=R;

                while (i < j) {
                    if (height[i] > 0 && height[L] == 0) {
                        L = i;
                    }
                    if (height[j] > 0 && height[R] == 0) {
                        R = j;
                    }
                    i++;
                    j--;
                }

                int A=L,B=R;
                i=A+1;
                j=B-1;

                for (int x = L+1; x < R-1; x++) {
                    int t = x;
                    if (height[t] > A) {
                        int temp = t;
                        t = A;
                        A = temp;
                    }

                    if (height[t] > B) {
                        B = t;
                    }
                }

                if (A > B ) {
                    int temp = A;
                    A = B;
                    B = temp;
                }

                sum += calcSum(height,A,B);

                while (B<R) {
                    int t = R;
                    for (int f = R-1; f > B; f--) {
                        if (height[f] > height[t]) {
                            t = f;
                        }
                    }
                    sum += calcSum(height,B,t);
                    B = t;
                }

                while (A>L) {
                    int t = L;
                    for (int f = L+1; f < A; f++) {
                        if (height[f] > height[t]) {
                            t = f;
                        }
                    }
                    sum += calcSum(height,t,A);
                    A = t;
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
        //ae(new int[]{0,1,0,2,1,0,1,3,2,1,2,1}, solution::trap,6);
        //ae(new int[]{5,4,1,2}, solution::trap,1);
        ae(new int[]{0,1,0,0}, solution::trap,1);
    }

}
