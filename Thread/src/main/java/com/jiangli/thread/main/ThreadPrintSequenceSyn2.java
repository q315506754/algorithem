package com.jiangli.thread.main;


import org.apache.commons.lang.ObjectUtils;

/**
 * n线程依次打印ABCD...
 * @author Jiangli
 * @date 2018/10/26 16:18
 */
public class ThreadPrintSequenceSyn2 {
    static int status = 0;

    public static void main(String[] args) {
        final int start=1;
        final int n = 3;
        final String[] locks = new String[n];
        for (int i = 0; i < n; i++) {
            locks[i] = (char)('A' + i)+"";
            //System.out.println(System.identityHashCode(locks[i]));
            System.out.println(ObjectUtils.identityToString(locks[i]));
        }
        status = start - 1;


        for (int i = 0; i < n; i++) {
            int finalI = i;
            new Thread(()->{
                while (true) {
                    String nextLock = locks[(finalI + 1) % n];
                    String thisLock = locks[finalI];

                    synchronized (locks) {
                        if (finalI == status) {
//                            synchronized (nextLock) {
//                                nextLock.notifyAll();
//                            }

                            System.out.print(thisLock);

                            status = (status + 1)%n;
                        } else {
//                            synchronized (thisLock) {
//                                try {
//                                    thisLock.wait();
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                            }
                        }
                    }

                }
            }).start();
        }

    }

}
