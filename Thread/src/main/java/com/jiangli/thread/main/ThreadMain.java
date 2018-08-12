package com.jiangli.thread.main;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Jiangli
 * @date 2018/5/24 22:02
 */
public class ThreadMain {
    public  static void main(String[] args) {

        Thread.dumpStack();
        Thread thread = new Thread(() -> {
            int i = 0;
            while (i < 7) {
                i++;
                System.out.println("i="+i);

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(() -> {
            int i = 0;
            while (i < 9999) {
                i++;
                System.out.println("i2="+i);

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        thread2.start();

        System.out.println("main");

        try {
            Thread.sleep(3000L);

        System.out.println("wake");

            thread.join();
//            Thread.currentThread().wait();

//            thread.join(3000);

        System.out.println("joined");

            LockSupport.park();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
