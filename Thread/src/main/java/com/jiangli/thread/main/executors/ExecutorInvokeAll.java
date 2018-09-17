package com.jiangli.thread.main.executors;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author Jiangli
 * @date 2018/9/17 15:02
 */
public class ExecutorInvokeAll extends ExecutorTestBase {
    public static void main(String[] args) {
        ExecutorService x = getExecutorService();
        System.out.println(x);

        try {
            List<Future<Integer>> futures = x.invokeAll(getCallableList());
            System.out.println("submit...");

            while (true) {
                System.out.println("------------");
                for (Future<Integer> future : futures) {
                    System.out.println(future.isDone());
                }
                Thread.sleep(1000L);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
