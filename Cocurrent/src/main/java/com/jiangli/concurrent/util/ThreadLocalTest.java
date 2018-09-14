package com.jiangli.concurrent.util;

import java.util.concurrent.locks.LockSupport;

/**
 * Created by Jiangli on 2016/7/10.
 */
public class ThreadLocalTest {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("start...");

        ThreadLocal<String> local = new ThreadLocal<>();
        int n = 3;
        while (n-- > 0) {
            int finalN = n;
            new Thread(() -> {
                int x = 3;

                while (x-- > 0) {
                    String s = local.get();
                    if (s == null) {
                        String value = "local_name_n_" + finalN;
                        local.set(value);
                        System.out.println("set ThreadLocal："+value);
                    } else {
                        System.out.println("get from ThreadLocal："+s);
                    }
                    try {
                        Thread.sleep(1000L);
                    } catch (Exception e) {}
                }
            }).start();
        }

        LockSupport.park();
    }
}
