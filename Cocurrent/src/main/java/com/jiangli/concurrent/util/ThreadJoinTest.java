package com.jiangli.concurrent.util;

/**
 * Created by Jiangli on 2016/7/10.
 */
public class ThreadJoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.println(Thread.currentThread().getName()+" sub thread print...");
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
        System.out.println("ddd");
        System.out.println(Thread.currentThread().getName());
        thread.join(3333);
        thread.interrupt();
        System.out.println("over");
    }
}
