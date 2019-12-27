package com.jiangli.leetcode.test.multithread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author Jiangli
 * @date 2019/8/7 17:02
 */
public class Q1114_Seq3 {
    class Foo {
        Semaphore second = new Semaphore(0);
        Semaphore third = new Semaphore(0);

        public Foo() {

        }

        public void first(Runnable printFirst) throws InterruptedException {

            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            second.release();
        }

        public void second(Runnable printSecond) throws InterruptedException {
            second.acquire();
            printSecond.run();
            third.release();
        }

        public void third(Runnable printThird) throws InterruptedException {
            third.acquire();
            printThird.run();
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

            execute(executorService, three, foo::third);
            execute(executorService, two, foo::second);
            execute(executorService, one, foo::first);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //LockSupport.park();
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
