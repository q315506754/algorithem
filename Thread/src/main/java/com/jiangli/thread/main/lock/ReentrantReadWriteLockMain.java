package com.jiangli.thread.main.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Jiangli
 * @date 2018/5/22 20:43
 */
public class ReentrantReadWriteLockMain {
    public static void main(String[] args) {
        //也分为公平非公平版本
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

    }

}
