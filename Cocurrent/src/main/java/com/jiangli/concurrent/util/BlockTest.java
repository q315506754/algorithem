package com.jiangli.concurrent.util;

/**
 * Created by Jiangli on 2016/7/10.
 */
public class BlockTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("start...");
        final Object lock = new Object();
        new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(999999L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        Thread.sleep(1000L);
        synchronized (lock) {
            lock.wait();
        }
    }
}
