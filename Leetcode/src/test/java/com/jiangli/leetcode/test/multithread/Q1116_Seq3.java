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
public class Q1116_Seq3 {
    class ZeroEvenOdd {
        private int n;
        private int current = 1;
        Semaphore even = new Semaphore(0);
        Semaphore odd = new Semaphore(0);
        Semaphore zero = new Semaphore(1);

        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public void zero(IntConsumer printNumber) throws InterruptedException {
            while (true) {
                zero.acquire();

                if(current <= n){

                    printNumber.accept(0);

                    if (current  % 2 == 0) {
                        even.release();
                    } else {
                        odd.release();
                    }

                } else {
                    break;
                }

            }

        }

        public void even(IntConsumer printNumber) throws InterruptedException {
            while (true) {
                even.acquire();

                if(current <= n){
                    printNumber.accept(current++);
                    zero.release();
                } else {
                    break;
                }
            }
        }

        public void odd(IntConsumer printNumber) throws InterruptedException {
            while (true) {
                odd.acquire();

                if(current <= n){
                    printNumber.accept(current++);
                    zero.release();
                } else {
                    break;
                }
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
