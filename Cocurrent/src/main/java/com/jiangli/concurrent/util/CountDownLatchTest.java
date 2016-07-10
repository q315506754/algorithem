package com.jiangli.concurrent.util;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Jiangli on 2016/7/8.
 */
public class CountDownLatchTest {
    public static void main(String[] args) throws InterruptedException {

        int count = 10;
        CountDownLatch countDownLatch = new CountDownLatch(count);
        while (count-- > 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            }).start();
        }

        System.out.println("await..");
        countDownLatch.await();

        System.out.println("over..");


    }
}
