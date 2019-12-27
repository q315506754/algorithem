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
public class Q1116_Seq {
    class ZeroEvenOdd {
        private int n;
        private int current = 1;
        Semaphore even = new Semaphore(0);
        Semaphore odd = new Semaphore(0);
        Semaphore zero = new Semaphore(0);

        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            while (current <= n) {
                //if ((String.valueOf(current).length() & 0x1) == 1) {
                    printNumber.accept(0);
                //}

                if ((current & 0x1) == 0) {
                    even.release();
                } else {
                    odd.release();
                }

                zero.acquire();
                current += 1;
            }

        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            while (current <= n) {
                even.acquire();
                printNumber.accept(current);
                zero.release();
            }
        }

        public void odd(IntConsumer printNumber) throws InterruptedException {
            while (current <= n) {
                odd.acquire();
                printNumber.accept(current);
                zero.release();
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

    private void execute(ExecutorService executorService, ConsumeRunnable consumerR, IntConsumer consumer, CountDownLatch countDownLatch) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    consumerR.accept(consumer);
                    //countDownLatch.countDown();
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
