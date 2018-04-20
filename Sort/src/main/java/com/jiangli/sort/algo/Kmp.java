package com.jiangli.sort.algo;

import java.util.Arrays;

/**
 * @author Jiangli
 * @date 2018/4/14 11:52
 */
public class Kmp {
    public static final int[] next(String str) {
        int[] ret = new int[str.length()];// 若i=5 算的其实是 0~3的公共前缀 值域 0~2
        ret[0] = -1;//初始值很重要 原本是计算当前从0~index的公共前缀   现在变成 0~index-1
        //这里长度左移了
        for (int i = 1; i < str.length(); i++) {
            int index = ret[i-1];// 若i=5 算的其实是 0~3的公共前缀

            //因为index左移了1位 这里要+1比较
            while (index >= 0 && str.charAt(i) != str.charAt(index+1)) {
                index = ret[index];
            }

            //因为index左移了1位 这里要+1比较
            if (str.charAt(i) == str.charAt(index+1)) {
                ret[i] = index + 1;
            } else {
                ret[i] = -1;
            }

        }
        return ret;
    }

    public static final int[] next2(String str) {
        int[] ret = new int[str.length()];
        ret[0] = 0;//初始值很重要

        for (int i = 1; i < str.length(); i++) {
            int length = ret[i-1];//
            while (length > 0 && str.charAt(i) != str.charAt(length)) {
                length = ret[length];
            }

            //若i-1的length 和 i 对应的值相等，则i = length + 1
            if (str.charAt(i) == str.charAt(length)) {
                ret[i] = length + 1;
            } else {
                ret[i] = 0;
            }

        }
        return ret;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(next("abaabcaba")));
        System.out.println(Arrays.toString(next("aaaaaaaa")));
        System.out.println(Arrays.toString(next("ababa")));

        System.out.println(Arrays.toString(next2("abaabcaba")));
        System.out.println(Arrays.toString(next2("aaaaaaaa")));
        System.out.println(Arrays.toString(next2("ababa")));
    }

}
