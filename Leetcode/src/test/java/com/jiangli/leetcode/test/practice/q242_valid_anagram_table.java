package com.jiangli.leetcode.test.practice;

/**
 *
 给定两个字符串 s 和 t ，编写一个函数来判断 t 是否是 s 的字母异位词。

 示例 1:

 输入: s = "anagram", t = "nagaram"
 输出: true
 示例 2:

 输入: s = "rat", t = "car"
 输出: false
 说明:
 你可以假设字符串只包含小写字母。

 进阶:
 如果输入字符串包含 unicode 字符怎么办？你能否调整你的解法来应对这种情况？

 1  暴力法 Map<Char,Integer> O(s+t)
 2 暴力法 sort compare O(logn)


 */
public class q242_valid_anagram_table {
    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] cache = new int[26];
        for (int i = 0; i < s.length(); i ++) {
            cache[s.charAt(i)-'a']++;
        }

        for (int i = 0; i < t.length(); i ++) {
            int after = --cache[t.charAt(i)-'a'];
            if (after < 0) {
                return false;
            }
        }
        return true;
    }
}
