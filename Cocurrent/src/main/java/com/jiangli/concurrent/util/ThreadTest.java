package com.jiangli.concurrent.util;

/**
 * Created by Jiangli on 2016/7/10.
 */
public class ThreadTest {
    public static void main(String[] args) throws InterruptedException {
        Object obj1 = new Object();
        Object obj2 = new Object();
        synchronized (obj1) {
            obj2.wait();
        }
        System.out.println("over");
    }
}
