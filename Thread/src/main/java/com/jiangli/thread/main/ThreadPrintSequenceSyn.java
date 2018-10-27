package com.jiangli.thread.main;


import org.apache.commons.lang.ObjectUtils;

/**
 * n线程依次打印ABCD...
 * @author Jiangli
 * @date 2018/10/26 16:18
 */
public class ThreadPrintSequenceSyn {
    public static void main(String[] args) {
        final int start=1;
        final int n = 3;
        final String[] locks = new String[n];
        for (int i = 0; i < n; i++) {
            locks[i] = (char)('A' + i)+"";
            //System.out.println(System.identityHashCode(locks[i]));
            System.out.println(ObjectUtils.identityToString(locks[i]));
        }

        for (int i = 0; i < n; i++) {
            int finalI = i;
            new Thread(()->{
                while (true) {
                    String preLock = locks[(finalI - 1 + n) % n];
                    String thisLock = locks[finalI];
                    synchronized (preLock) {
                        synchronized (thisLock) {
                            System.out.print(thisLock);

                            thisLock.notify();
                        }

                        try {
                            preLock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();

//            需要手动控制他们三个的启动顺序，即Thread.Sleep(100)。
//            try {
//                Thread.sleep(100L);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

    }

}
