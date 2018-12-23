package com.jiangli.thread.main;


import org.apache.commons.lang.ObjectUtils;

import java.util.concurrent.Semaphore;

/**
 * n线程依次打印ABCD...
 *
 * @author Jiangli
 * @date 2018/10/26 16:18
 */
public class ThreadPrintSequenceSema {
    public static void main(String[] args) {
        final int start = 1;
        final int n = 3;
        final String[] str = new String[n];
        final Semaphore[] cons = new Semaphore[n];
        for (int i = 0; i < n; i++) {
            str[i] = (char) ('A' + i) + "";
            cons[i] = new Semaphore(0);

            if (i == (start-2+n)%n) {
                cons[i].release(1);
            }
            System.out.println(ObjectUtils.identityToString(str[i]));
        }

        for (int i = 0; i < n; i++) {
            final int finalI = i;

            new Thread(() -> {
                int loop = 15;

                try {
//                    while (loop-- > 0) {
                    while (true) {
                        int prevIdx = (finalI - 1 + n) % n;
                        cons[prevIdx].acquire();

                        System.out.print(str[finalI]);
//                    System.out.print(str[finalI]+"("+Thread.currentThread().getName()+"="+finalI+" on "+prevIdx+")-");
                        if (finalI == n - 1) {
                            System.out.println(loop);
                        }

                        cons[finalI].release(1);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }



    }

}
