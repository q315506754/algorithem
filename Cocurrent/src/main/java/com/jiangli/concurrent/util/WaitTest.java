package com.jiangli.concurrent.util;

/**
 * Created by Jiangli on 2016/7/10.
 */
public class WaitTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("start...");
        Object lock = new Object();
        synchronized (lock) {
            lock.wait();
        }
    }
}
