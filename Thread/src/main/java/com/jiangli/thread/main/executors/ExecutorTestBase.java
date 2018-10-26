package com.jiangli.thread.main.executors;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jiangli
 * @date 2018/9/17 14:42
 */
public class ExecutorTestBase {
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static class A implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 5;
        }
    }

    static class B implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            try {
                Thread.sleep(7000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 7;
        }
    }

     static ExecutorService getExecutorService() {
        return Executors.newFixedThreadPool(3);
    }
     static ExecutorService getcheduledExecutorService() {
        return Executors.newScheduledThreadPool(3);
    }

     static List<Callable<Integer>> getCallableList() {
        return Arrays.asList(new A(), new B());
    }

    public static void main(String[] args) {

    }

}
