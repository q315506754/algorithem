package com.jiangli.concurrent.util;

import java.util.concurrent.Semaphore;

/**
 * Created by Jiangli on 2016/7/10.
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    semaphore.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        try {
            semaphore.acquire();
            System.out.println("rest:"+semaphore.availablePermits());
            semaphore.acquire();
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("over");
    }
}
