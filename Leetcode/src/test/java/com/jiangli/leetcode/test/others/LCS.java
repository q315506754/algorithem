package com.jiangli.leetcode.test.others;

import com.jiangli.leetcode.test.practice.PracticeBase;
import org.junit.Test;

import java.util.function.BiFunction;

/**
 * 最长公共子序列 LCS
 * 不要求连续
 */
//@RunWith(StatisticsJunitRunner.class)
public class LCS extends PracticeBase {

    class Solution {
        public String baoli(String s1, String s2) {
            int l1=s1.length(),l2=s2.length();

            String ret = "";
            for (int i = 0; i < l2; i++) {
                int fd = s1.indexOf(s2.charAt(i));
                if (fd >= 0 ) {
                    String cur = s2.charAt(i)+"";

                    if (fd+1<l1 && i+1 < l2) {
                        cur+= baoli(s1.substring(fd+1),s2.substring(i+1));
                    }

                    if (cur.length() > ret.length()) {
                        ret = cur;
                    }
                }
            }

            return ret;
        }
    }

    public void test_case(BiFunction<String,String,String> consumer) {
        ae("HelloWorld","loop", consumer, "loo");
        ae("loop","HelloWorld", consumer, "loo");
        ae("123456778","357486782", consumer, "35778");
        ae("123456778","32345678", consumer, "2345678");
    }

    //@RepeatFixedDuration
    @Test
    public void test_baoli() {
        //shouldPrint(false);
        test_case(new Solution()::baoli);
    }

}
