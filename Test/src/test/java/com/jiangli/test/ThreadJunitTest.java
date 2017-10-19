package com.jiangli.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class ThreadJunitTest {
        private long startTs;

        @Before
        public void before() {
            startTs = System.currentTimeMillis();
            System.out.println("----------before-----------");
            System.out.println();
        }

        @After
        public void after() {
            long cost = System.currentTimeMillis() - startTs;
            System.out.println("----------after-----------:cost:"+cost+" ms");
            System.out.println();
        }

        @Test
        public void test_aa() {
            System.out.println(Arrays.toString(Thread.currentThread().getStackTrace()));
            for (StackTraceElement stackTraceElement : Thread.currentThread().getStackTrace()) {
                System.out.println(stackTraceElement);
            }

            Thread thread = new Thread(() -> {
                while (true) {
                    System.out.println("aaaa");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            System.out.println(thread.isDaemon());
            System.out.println(Thread.currentThread().isDaemon());

//            throw new NullPointerException();
        }
}
