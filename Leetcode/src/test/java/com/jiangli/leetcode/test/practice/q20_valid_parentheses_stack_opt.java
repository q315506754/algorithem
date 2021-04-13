package com.jiangli.leetcode.test.practice;

import java.util.HashMap;
import java.util.Map;
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
public class q20_valid_parentheses_stack_opt {
    public boolean isValid(String s) {
        Stack<String> stack = new Stack<>();
        Map<String, String> map = new HashMap<>();
        map.put("(",")");
        map.put("[","]");
        map.put("{","}");
        for (int i = 0; i < s.length(); i++) {
            String curS = s.substring(i,i+1);
            String right = map.get(curS);
            if (right != null) {
                //stack.push(curS);
                stack.push(right);
                continue;
            }

            if (stack.empty()) {
                return false;
            }

            if (!stack.pop().equals(curS)) {
                return false;
            }
            //stack.pop();
        }
        return stack.empty();
    }
}
