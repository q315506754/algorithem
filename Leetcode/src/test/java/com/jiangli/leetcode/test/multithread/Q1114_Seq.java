package com.jiangli.leetcode.test.multithread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Jiangli
 * @date 2019/8/7 17:02
 */
public class Q1114_Seq {
    class Foo {
        private volatile AtomicInteger status = new AtomicInteger(1);
        List<Thread> threads = new ArrayList<Thread>();

        public Foo() {

        }

        public void release() {

            for (Thread thread : threads) {
                LockSupport.unpark(thread);
            }
        }

        public void first(Runnable printFirst) throws InterruptedException {
            Thread e = Thread.currentThread();
            threads.add(e);

            // printFirst.run() outputs "first". Do not change or remove this line.
            while (true) {
                if (status.compareAndSet(1, 2)) {
                    System.out.print("[1]" + status.get());
                    printFirst.run();
                    release();
                    break;
                } else {
                    LockSupport.park();
                }
            }
        }

        public void second(Runnable printSecond) throws InterruptedException {
            threads.add(Thread.currentThread());

            // printSecond.run() outputs "second". Do not change or remove this line.
            while (true) {
                if (status.compareAndSet(2, 3)) {
                    System.out.print("[2]" + status.get());
                    printSecond.run();
                    release();
                    break;
                } else {
                    LockSupport.park();
                }
            }
        }

        public void third(Runnable printThird) throws InterruptedException {
            threads.add(Thread.currentThread());

            // printThird.run() outputs "third". Do not change or remove this line.
            while (true) {
                if (status.compareAndSet(3, 1)) {
                    System.out.print("[3]" + status.get());
                    printThird.run();
                    release();
                    break;
                } else {
                    LockSupport.park();
                }
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

        Runnable one = createRunnable("one", countDownLatch);
        Runnable two = createRunnable("two", countDownLatch);
        Runnable three = createRunnable("three", countDownLatch);


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
