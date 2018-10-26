package com.jiangli.thread.main;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jiangli
 * @date 2018/5/22 9:23
 */
public class LockObjMain {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock();
        lock.lock();
        Condition condition = lock.newCondition();

        try {
            System.out.println("before signal");
            condition.signal();
            System.out.println("before wait");
            condition.await();
            System.out.println("after wait");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
