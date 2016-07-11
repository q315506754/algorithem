package com.jiangli.concurrent.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jiangli on 2016/7/10.
 */
public class AtomicIntTEst {
    static class NotAtomicCounter{
        int i;

        public void inc(){
            i++;
        }

    }
    public static void main(String[] args) throws InterruptedException {
//        Unsafe unsafe = Unsafe.getUnsafe();
//        System.out.println(unsafe);

        int count = 10000;
//        int count = 1;
        NotAtomicCounter counter = new NotAtomicCounter();
        ExecutorService executorService = Executors.newFixedThreadPool(50);
        CountDownLatch countDownLatch = new CountDownLatch(count);

        Runnable command = new Runnable() {
            @Override
            public void run() {
                counter.inc();
                countDownLatch.countDown();
            }
        };
        while (count-- > 0) {
            executorService.execute(command);
        }

        System.out.println("waiting");
        countDownLatch.await();
        System.out.println("result:"+counter.i);

    }
}