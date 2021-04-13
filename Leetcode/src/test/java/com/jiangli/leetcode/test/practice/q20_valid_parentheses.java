package com.jiangli.leetcode.test.practice;

/**
 20. 有效的括号
 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。

 有效字符串需满足：

 左括号必须用相同类型的右括号闭合。
 左括号必须以正确的顺序闭合。

 1.暴力replace
 2.栈
 */
public class q20_valid_parentheses {
    public boolean isValid(String s) {
        while (true) {
            int oldLen = s.length();
            s = s.replace("()", "");
            s = s.replace("{}", "");
            s = s.replace("[]", "");
            if (s.length() == oldLen) {
                break;
            }
        }
        return s.length() == 0;
    }
}
