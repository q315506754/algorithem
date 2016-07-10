package com.jiangli.concurrent.util;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Jiangli on 2016/7/9.
 */
public class ReentrantReadWriteLockTest2 {
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
                            readLock.lock();
                            System.out.println("get read lock...");
                            Thread.sleep(10000);
                        } finally {
                            readLock.unlock();
                            System.out.println("read unlock...");
                        }

                        System.out.println("read...");
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
                            readLock.lock();
                            System.out.println("get read lock2...");
                            Thread.sleep(10000);
                        } finally {
                            readLock.unlock();
                            System.out.println("read unlock2...");
                        }

                        System.out.println("read2...");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
