package com.jiangli.sort.algo;

import com.jiangli.common.utils.LogicUtil;

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

    // O( len(src) * len(pattern) )
    // 每次外层循环都要跳下一位重头开始匹配，效率较低，有没有可能跳过几位开始匹配？
    //有没有办法能利用前一次匹配的结果？
    //KMP的重点就在于当某一个字符与主串不匹配时，我们应该知道j指针（下一次外层循环） 要移动到哪？
    public static final int simplefind(String src, String pattern) {
        if (LogicUtil.anyNull(src,src)) {
            return -1;
        }
        if (pattern.length() > src.length()) {
            return -1;
        }

        o:
        for (int i = 0; i < src.length(); i++) {
            for (int j = 0; j < pattern.length(); j++) {
                if (src.charAt(i+j) != pattern.charAt(j)) {
                    continue o;
                }
            }
            return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(next("abaabcaba")));
        System.out.println(Arrays.toString(next("aaaaaaaa")));
        System.out.println(Arrays.toString(next("ababa")));

        System.out.println(Arrays.toString(next2("abaabcaba")));
        System.out.println(Arrays.toString(next2("aaaaaaaa")));
        System.out.println(Arrays.toString(next2("ababa")));

        System.out.println(simplefind("asdfghxzzc","fgh"));
        System.out.println(simplefind("asdfghxzzc","asd"));
        System.out.println(simplefind("asdfghxzzc","zzc"));
        System.out.println(simplefind("asdfghxzzc","xx"));

    }

}
