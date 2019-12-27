package com.jiangli.jvmtest;

/**
 * @author Jiangli
 * @date 2019/5/24 9:41
 */
public class StackOverflowTest {
    public static void func() {
        func();
    }

    /**
     * Exception in thread "main" java.lang.StackOverflowError
     * @param args
     */
    public static void main(String[] args) {
        func();
    }
}
