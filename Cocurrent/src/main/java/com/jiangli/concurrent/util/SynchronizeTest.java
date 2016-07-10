package com.jiangli.concurrent.util;

/**
 * Created by Jiangli on 2016/7/9.
 */
public class SynchronizeTest {
    public static void main(String[] args) {
        synchronized (SynchronizeTest.class) {
            System.out.println("over");
        }
    }
}
