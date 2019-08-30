package com.jiangli.leetcode.test.multithread;

import com.jiangli.junit.spring.StatisticsJunitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *
 * Ca2Co3
 * CaCaCOOO
 *
 * Xm Yn Zo Wp
 * 公倍数 m,n,o,p
 * 线程数分别  m,n,o,p
 * 每个线程产生资源分别  公倍数/(m,n,o,p)
 *
 * 最大公约数
 *辗转相减法
 *
 *
 * @author Jiangli
 * @date 2019/8/7 17:02
 */
@RunWith(StatisticsJunitRunner.class)
public class Q1117_H2O {
    class H2O {
        Semaphore o = new Semaphore(2);
        Semaphore h = new Semaphore(0);

        public H2O() {

        }

        public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
            // releaseHydrogen.run() outputs "H". Do not change or remove this line.
            o.acquire();
            releaseHydrogen.run();
            h.release();
        }

        public void oxygen(Runnable releaseOxygen) throws InterruptedException {
            // releaseOxygen.run() outputs "H". Do not change or remove this line.
            h.acquire(2);
            releaseOxygen.run();
            o.release(2);
        }
    }



    @Test
    //@RepeatFixedDuration
    public void test_() throws InterruptedException {
        H2O foo = new H2O();

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        int times =10;
        CountDownLatch countDownLatch = new CountDownLatch(3*times);
        Runnable h = createRunnable("H", countDownLatch);
        Runnable o = createRunnable("O", countDownLatch);

        try {
            while (times-- > 0) {
                execute(executorService, foo::hydrogen, h, countDownLatch);
                execute(executorService, foo::oxygen, o, countDownLatch);
                execute(executorService, foo::hydrogen, h, countDownLatch);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //LockSupport.park();
        countDownLatch.await();
        //foo.status.compareAndSet(1, 2);

        //System.out.println(foo.status);
        //System.out.println(foo.status.get());
    }

    public Runnable createRunnable(String str, CountDownLatch countDownLatch) throws InterruptedException {
        return new Runnable() {
            @Override
            public void run() {
                System.out.print(str);
                countDownLatch.countDown();
            }
        };
    }


    private void execute(ExecutorService executorService, ConsumeRunnable consumerR, Runnable consumer, CountDownLatch countDownLatch) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    consumerR.accept(consumer);
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    interface ConsumeRunnable {
        void accept(Runnable t) throws InterruptedException;
    }


}
