package com.jiangli.sort.algo;

import java.util.Arrays;

/**
 * @author Jiangli
 * @date 2018/4/14 11:52
 */
public class Kmp {
    public static final int[] next(String str) {
        int[] ret = new int[str.length()];
        ret[0] = -1;

        for (int i = 1; i < str.length(); i++) {
            int index = ret[i-1];
            while (index >= 0 && str.charAt(i) != str.charAt(index+1)) {
                index = ret[index];
            }

            if (str.charAt(i) == str.charAt(index+1)) {
                ret[i] = index + 1;
            } else {
                ret[i] = -1;
            }

        }
        return ret;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(next("abaabcaba")));
        System.out.println(Arrays.toString(next("aaaaaaaa")));
        System.out.println(Arrays.toString(next("ababa")));
    }

}
