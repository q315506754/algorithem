package com.jiangli.thread.main;


import org.apache.commons.lang.ObjectUtils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * n线程依次打印ABCD...
 *
 * @author Jiangli
 * @date 2018/10/26 16:18
 */
public class ThreadPrintSequenceLock {
    public static void main(String[] args) {
        final int start = 1;
        final int n = 3;
        Lock lock = new ReentrantLock();
        final String[] str = new String[n];
        final Condition[] cons = new Condition[n];
        for (int i = 0; i < n; i++) {
            str[i] = (char) ('A' + i) + "";
            cons[i] = lock.newCondition();

            System.out.println(ObjectUtils.identityToString(str[i]));
        }


//        for (int i = 0; i < n; i++) {
        for (int i = n - 1; i >= 0; i--) {
            final int finalI = i;

            new Thread(() -> {
                int loop = 15;
                lock.lock();

                try {
                    while (loop-- > 0) {
//                    while (true) {
                        int nextIdx = (finalI + 1 + n) % n;
//                    System.out.print(str[finalI]+"("+Thread.currentThread().getName()+"="+finalI+" on "+nextIdx+")-");
                        System.out.print(str[finalI]);
                        if (finalI == n - 1) {
                            System.out.println(loop);
                        }

                        Condition nextLock = cons[nextIdx];
                        Condition thisLock = cons[finalI];

                        nextLock.signal();

                        thisLock.await();
                    }
                } catch (Exception e) {
                    lock.unlock();
                }
            }).start();

//            if (i == 0) {
//                try {
//                    Thread.sleep(10L);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        }

    }

}
