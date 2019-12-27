package com.jiangli.leetcode.test.practice;

import org.junit.Test;

/**
 * 最长回文
 */
//@RunWith(StatisticsJunitRunner.class)
public class q5_huiwen2 extends PracticeBase {

    class Solution {
        public String longestPalindrome(String s) {
            int length = s.length();
            int maxLength = 0;
            String str = "";


            if (length > 0) {
                int maxIndex = 0;
                int[] record = new int[length+1];
                record[0] = 1;

                for (int i = 0; i < length - maxLength; i++) {
                    char first = s.charAt(i);

                    int interruput = -1;

                    int minLengthIfI =  record[i] - 2;
                    if (minLengthIfI < 1) {
                        minLengthIfI =1;
                    }

                    //j最小可以到前一位回文长度 - 2
                    for (int j = length-1; j >= i + minLengthIfI - 1 ; j--) {

                        if (s.charAt(j) == first) {
                            int m = i+1,n=j-1;
                            while (m<=n && s.charAt(m) == s.charAt(n)) {
                                m++;
                                n--;
                            }

                            //说明范围内字符满足回文条件
                            if (m>n) {
                                int newLength = j - i + 1;
                                //计算出该位回文长度
                                record[i+1] =newLength;

                                if (newLength > maxLength) {
                                    maxIndex = i;
                                    maxLength = newLength;
                                    //str = s.substring(i, j + 1);
                                }
                                break;
                            } else {
                                if (interruput == -1) {
                                    interruput = n;
                                } else {
                                    //优化跳转
                                    //跟奇偶性有关
                                    //连续两次判断不通过可以跳到第一次中断点的下一位判断
                                    j=interruput;
                                    interruput = -1;
                                }
                            }

                        } else {
                            interruput = -1;
                        }
                    }

                }//end of for

                str = s.substring(maxIndex, maxIndex + record[maxIndex+1]);
            }

            return str;
        }
    }

    //@RepeatFixedDuration
    @Test
    public void test_() {
        //shouldPrint(false);

        Solution solution = new Solution();
        ae("babad", solution::longestPalindrome, "bab","aba");
        ae("cbbd", solution::longestPalindrome, "bb");
        ae("", solution::longestPalindrome, "");
        ae("a", solution::longestPalindrome, "a");
        ae("aaabaaaa", solution::longestPalindrome, "aaabaaa");
    }

}
