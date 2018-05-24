package com.jiangli.thread.main;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jiangli
 * @date 2018/5/22 13:56
 */
public class ConditionMain {
    public static void main(String[] args) {
        //    与lock配套使用
        //    增强了object monitor，比如带 顺序唤醒，无需锁等待功能
        //    与monitor独立，可以对condition使用synchronized，但不必要
        //    可能出现虚假唤醒，所以有必要使用 条件循环await
        ReentrantLock lock = new ReentrantLock();
        Condition condition357 = lock.newCondition();
        Condition conditionPrint = lock.newCondition();


        Integer[] ii = new Integer[1];

        //producer
        new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                if (i % 13579 == 0) {
                    try {
                        lock.lock();

                        while(ii[0] != null)
                            conditionPrint.await();

                        System.out.println("produce:" + i);
                        ii[0] = i;

                        condition357.signalAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        }).start();

        //consumer
        new Thread(() -> {
            while (true) {
                try {
                    lock.lock();

                    while(ii[0] == null)
                        condition357.await();

                    System.out.println("consume():" + ii[0]);
                    ii[0]=null;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    conditionPrint.signalAll();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }

            }
        }).start();
    }

}
