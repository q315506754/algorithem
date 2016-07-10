package com.jiangli.concurrent.util;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Jiangli on 2016/7/9.
 */
public class ReentrantReadWriteLockTest3 {
    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
//                        Thread.sleep(1000);
                        try {
                            writeLock.lock();
                            System.out.println("get writeLock lock...");
                            Thread.sleep(10000);
                        } finally {
                            writeLock.unlock();
                            System.out.println("writeLock unlock...");
                        }

                        System.out.println("write...");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
//                        Thread.sleep(1000);
                        try {
                            writeLock.lock();
                            System.out.println("get writeLock lock2...");
                            Thread.sleep(10000);
                        } finally {
                            writeLock.unlock();
                            System.out.println("writeLock unlock2...");
                        }

                        System.out.println("write2...");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
