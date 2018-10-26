package com.jiangli.thread.main;


import org.apache.commons.lang.ObjectUtils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * n线程依次打印ABCD...
 * @author Jiangli
 * @date 2018/10/26 16:18
 */
public class ThreadPrintSequenceLock {
    public static void main(String[] args) {
        final int start=1;
        final int n = 3;
        Lock lock = new ReentrantLock();
        final String[] str = new String[n];
        final Condition[] cons = new Condition[n];
        for (int i = 0; i < n; i++) {
            str[i] = (char)('A' + i)+"";
            cons[i] = lock.newCondition();

            System.out.println(ObjectUtils.identityToString(str[i]));
        }

        Condition startCondition = lock.newCondition();

        for (int i = 0; i < n; i++) {
            final int finalI = i;

            new Thread(()->{
                int loop = 15;
                while (loop-->0) {
//                while (true) {
                    lock.lock();

//                    if (start-1==finalI) {
//
//                    } else {
//                        try {
//                            startCondition.await();
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }

                    int prevIdx = (finalI - 1 + n) % n;
//                    System.out.print(str[finalI]+"("+Thread.currentThread().getName()+"="+finalI+" on "+prevIdx+")-");
                    System.out.print(str[finalI]);
//                    if (finalI == n-1) {
//                        System.out.println(loop);
//                    }

                    Condition preLock = cons[prevIdx];
                    Condition thisLock = cons[finalI];

                    try {
                        thisLock.signal();



                        preLock.await();
                    } catch (Exception e) {
                        lock.unlock();
                    }
                }
            }).start();
        }

    }

}
