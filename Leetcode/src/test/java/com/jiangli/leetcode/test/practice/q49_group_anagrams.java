package com.jiangli.leetcode.test.practice;

import java.util.*;

/**
 *
 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。

 示例:

 输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
 输出:
 [
 ["ate","eat","tea"],
 ["nat","tan"],
 ["bat"]
 ]
 说明：

 所有输入均为小写字母。
 不考虑答案输出的顺序。

 1  暴力法 sort compare + Map<String,List<Integer>> O(s+t)


 */
public class q49_group_anagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> cache = new HashMap<>();

        for (int i = 0; i < strs.length; i++) {
            char[] chars = strs[i].toCharArray();
            Arrays.sort(chars);
            String s = new String(chars);
            List<String> strings = cache.get(s);
            if (strings == null) {
                strings = new ArrayList<>();
                cache.put(s, strings);
            }
            strings.add(strs[i]);
        }

        List<List<String>> ret = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : cache.entrySet()) {
            List<String> value = entry.getValue();
            ret.add(value);
        }

        return ret;
    }
}
