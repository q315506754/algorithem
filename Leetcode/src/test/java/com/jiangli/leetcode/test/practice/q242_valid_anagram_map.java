package com.jiangli.leetcode.test.practice;

import java.util.HashMap;
import java.util.Map;

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
public class q242_valid_anagram_map {
    public boolean isAnagram(String s, String t) {
        char[] charsS = s.toCharArray();
        Map<Character, Integer> cache = new HashMap<>();
        for (char c : charsS) {
            cache.compute(c,(character, integer) -> integer == null?1:integer + 1);
        }

        char[] charsT = t.toCharArray();
        for (char c : charsT) {
            cache.compute(c,(character, integer) -> integer == null?-1:integer-1);
        }

        for (Map.Entry<Character, Integer> entry : cache.entrySet()) {
            if (entry.getValue().intValue() != 0) {
                return false;
            }
        }
        return true;
    }
}
