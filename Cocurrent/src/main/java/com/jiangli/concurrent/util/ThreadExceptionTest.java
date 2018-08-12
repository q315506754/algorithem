package com.jiangli.concurrent.util;

/**
 * Created by Jiangli on 2016/7/10.
 */
public class ThreadExceptionTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getThreadGroup());
                System.out.println(Thread.currentThread().getName());
                throw new NullPointerException("aaa bbg");
            }
        });

        thread.setUncaughtExceptionHandler((t, e) -> {
            System.out.println(t);
            System.out.println(e);
        });

        thread.start();
    }
}
