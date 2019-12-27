package com.jiangli.leetcode.test.multithread;

import com.jiangli.junit.spring.RepeatFixedDuration;
import com.jiangli.junit.spring.StatisticsJunitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * @author Jiangli
 * @date 2019/8/7 17:02
 */
@RunWith(StatisticsJunitRunner.class)
public class Q1116_Seq4 {
    class ZeroEvenOdd {
        private final int n;
        private volatile int i = 1;
        private Semaphore even = new Semaphore(0);
        private Semaphore odd = new Semaphore(0);
        private Semaphore zero = new Semaphore(1);

        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            while (i <= n) {
                zero.acquire();
                if (i <= n) printNumber.accept(0);
                if (i % 2 != 0) {
                    odd.release(1);
                } else {
                    even.release(1);
                }
            }
        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            while (i <= n) {
                even.acquire(1);
                if (i <= n) printNumber.accept(i++);
                zero.release(1);
            }
        }

        public void odd(IntConsumer printNumber) throws InterruptedException {
            while (i <= n) {
                odd.acquire(1);
                if (i <= n) printNumber.accept(i++);
                zero.release(1);
            }
        }
    }


    @Test
    @RepeatFixedDuration
    public void test_() throws InterruptedException {
        ZeroEvenOdd foo = new ZeroEvenOdd(5);

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        IntConsumer consumer = n -> System.out.print(n);
        CountDownLatch countDownLatch = new CountDownLatch(3);

        try {
            execute(executorService, foo::even, consumer, countDownLatch);
            execute(executorService, foo::zero, consumer, countDownLatch);
            execute(executorService, foo::odd, consumer, countDownLatch);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //LockSupport.park();
        //countDownLatch.await();
        //foo.status.compareAndSet(1, 2);

        //System.out.println(foo.status);
        //System.out.println(foo.status.get());
    }

    @Test
    @RepeatFixedDuration
    public void test_2() throws InterruptedException {
        int i = 0;
        i++;
    }

    @Test
    @RepeatFixedDuration
    public void test_3() throws InterruptedException {
        int i = 0;
        i+=1;
    }

    @Test
    @RepeatFixedDuration
    public void test_4() throws InterruptedException {
        {{{
            int i = 0;
            i += 1;
        }}}
    }

    /**
     * ICONST_0
     * ISTORE 1
     *  ILOAD 1
     *  LDC 200000000
     *  IF_ICMPGE L2
     *  IINC 1 1
     *  GOTO L1
     *
     */
    @Test
    @RepeatFixedDuration
    public void test_group2() throws InterruptedException {
        int n = 0;
        while (n < 200000000) {
            n++;
        }
    }

    /**
     *
     *  ICONST_0
     *  ISTORE 1
     * ILOAD 1
     *  IINC 1 1
     * LDC 200000000
     * IF_ICMPGE L2
     *  GOTO L1
     */
    @Test
    @RepeatFixedDuration
    public void test_group2_2() throws InterruptedException {
        int n = 0;
        while (true) {
            if (n++ < 200000000) {

            } else {
                break;
            }
        }
    }


    private void execute(ExecutorService executorService, ConsumeRunnable consumerR, IntConsumer consumer, CountDownLatch countDownLatch) {
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
        void accept(IntConsumer printNumber) throws InterruptedException;
    }


}
