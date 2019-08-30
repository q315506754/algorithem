package com.jiangli.leetcode.test.multithread;

import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jiangli
 * @date 2019/8/7 17:02
 */
public class Q1115_Seq3 {
    class FooBar {
        private int n;
        private volatile boolean finish=false;
        public FooBar(int n) {
            this.n = n;
        }

        public void foo(Runnable printFoo) throws InterruptedException {

            for (int i = 0; i < n; i++) {

                // printFoo.run() outputs "foo". Do not change or remove this line.
                while(finish){
                    Thread.yield();
                }
                printFoo.run();
                finish=true;
            }
        }

        public void bar(Runnable printBar) throws InterruptedException {

            for (int i = 0; i < n; i++) {

                // printBar.run() outputs "bar". Do not change or remove this line.
                while (!finish){
                    Thread.yield();
                }
                printBar.run();
                finish=false;
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
        FooBar foo = new FooBar(10);

        CountDownLatch countDownLatch = new CountDownLatch(2);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Runnable fooR = createRunnable("foo", countDownLatch);
        Runnable barR = createRunnable("bar", countDownLatch);


        try {
            execute(executorService, fooR, foo::foo);
            execute(executorService, barR, foo::bar);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //LockSupport.park();
        countDownLatch.await();
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
