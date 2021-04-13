package com.jiangli.leetcode.test.practice;

import java.util.Stack;

/**
 20. 有效的括号
 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。

 有效字符串需满足：

 左括号必须用相同类型的右括号闭合。
 左括号必须以正确的顺序闭合。

 1.暴力replace
 2.栈
 */
public class q20_valid_parentheses_stack_opt2 {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            char cur = chars[i];
            if (cur == '(') {
                stack.push(')');
            }else if (cur == '{') {
                stack.push('}');
            }else if (cur == '[') {
                stack.push(']');
            } else if (stack.isEmpty() || stack.pop() != cur) {
                return false;
            }
        }
        return stack.empty();
    }
}
