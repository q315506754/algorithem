package com.jiangli.leetcode.test.practice;

import java.util.Stack;

/**
 155. 最小栈
 设计一个支持 push ，pop ，top 操作，并能在常数时间内检索到最小元素的栈。

 push(x) —— 将元素 x 推入栈中。
 pop() —— 删除栈顶的元素。
 top() —— 获取栈顶元素。
 getMin() —— 检索栈中的最小元素。

 1 2个栈,记录每个位置的最小数

 */
public class q155_min_stack_opt {
    class MinStack {
        Stack<Integer> stack;
        Stack<Integer> minStack;

        /** initialize your data structure here. */
        public MinStack() {
            stack = new Stack<>();
            minStack = new Stack<>();
        }

        public void push(int x) {
            stack.push(x);

            if (minStack.empty()) {
                minStack.push(x);
            } else {
                Integer curMin = minStack.peek();
                if (x<=curMin) {
                    minStack.push(x);
                }
            }
        }

        public void pop() {
            Integer pop = stack.pop();
            if (pop.equals(minStack.peek())) {
                minStack.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return minStack.peek();
        }
    }
}
