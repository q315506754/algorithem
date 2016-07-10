package com.jiangli.concurrent.util;

import java.util.concurrent.CyclicBarrier;

/**
 * Created by Jiangli on 2016/7/8.
 */
public class CyclicBarrierTest2 {
    public static void main(String[] args) {
        int parties = 10;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(parties);
        while (parties-->0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int i = 1;
                    while (true) {
                        try {
                            Thread.sleep(1000);
                            System.out.println(Thread.currentThread().getName()+"-await"+i);
                            cyclicBarrier.await();
                            System.out.println(Thread.currentThread().getName()+"-wait over"+i);
                            i++;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        }
        System.out.println("over");

    }
}
