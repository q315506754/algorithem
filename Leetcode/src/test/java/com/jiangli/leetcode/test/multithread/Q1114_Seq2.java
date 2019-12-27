package com.jiangli.leetcode.test.multithread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jiangli
 * @date 2019/8/7 17:02
 */
public class Q1114_Seq2 {
    class Foo {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        int status = 1;

        public Foo() {

        }

        public void first(Runnable printFirst) throws InterruptedException {

            // printFirst.run() outputs "first". Do not change or remove this line.
            try {
                lock.lock();

                while (status != 1) {
                    condition.await();
                }

                printFirst.run();
                status = 2;
                condition.signalAll();

            } finally {
                lock.unlock();
            }
        }

        public void second(Runnable printSecond) throws InterruptedException {
            try {
                lock.lock();

                while (status != 2) {
                    condition.await();
                }

                printSecond.run();
                status = 3;
                condition.signalAll();

            } finally {
                lock.unlock();
            }
        }

        public void third(Runnable printThird) throws InterruptedException {
            try {
                lock.lock();

                while (status != 3) {
                    condition.await();
                }

                printThird.run();
                status = 1;
                condition.signalAll();

            } finally {
                lock.unlock();
            }
        }
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

    @Test
    public void test_() throws InterruptedException {
        Foo foo = new Foo();

        CountDownLatch countDownLatch = new CountDownLatch(3);
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        Runnable three = createRunnable("three", countDownLatch);
        Runnable two = createRunnable("two", countDownLatch);
        Runnable one = createRunnable("one", countDownLatch);


        try {
            execute(executorService, three, foo::third);
            execute(executorService, two, foo::second);
            execute(executorService, one, foo::first);
        } catch (Exception e) {
            e.printStackTrace();
        }

        countDownLatch.await();
        //foo.status.compareAndSet(1, 2);

        //System.out.println(foo.status);
        //System.out.println(foo.status.get());
    }

    interface ConsumeRunnable {
        void accept(Runnable t) throws InterruptedException;
    }

    private void execute(ExecutorService executorService, Runnable one, ConsumeRunnable f) {
        //try {
        //    f.accept(one);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    f.accept(one);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
