package com.jiangli.concurrent.util;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Jiangli on 2016/7/8.
 */
public class CountDownLatchTest2 {
    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        int count = 10;
        while (count-- > 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("before await.."+Thread.currentThread().getName());
                        countDownLatch.await();
                        System.out.println("after await.."+Thread.currentThread().getName());

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        System.out.println("main await..");

        Thread.sleep(3000);
        countDownLatch.countDown();

        System.out.println("main over..");


    }
}
