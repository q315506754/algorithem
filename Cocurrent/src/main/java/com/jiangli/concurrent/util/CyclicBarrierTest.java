package com.jiangli.concurrent.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by Jiangli on 2016/7/8.
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        int parties = 10;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(parties);
        while (parties-->0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName()+"-await");
                        cyclicBarrier.await();
                        System.out.println(Thread.currentThread().getName()+"-wait over");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
        System.out.println("over");

        parties = 10;
        while (parties-->0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        System.out.println(Thread.currentThread().getName()+"-await2");
                        cyclicBarrier.await();
                        System.out.println(Thread.currentThread().getName()+"-wait over2");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
        System.out.println("over2");
    }
}
