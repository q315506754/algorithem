package com.jiangli.thread.main.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author Jiangli
 * @date 2018/9/17 15:02
 */
public class ExecutorSubmit extends ExecutorTestBase {
    public static void main(String[] args) {
        ExecutorService x = getExecutorService();
        System.out.println(x);

        A a = new A();
        B b = new B();

        try {
            Future<Integer> submitA = x.submit(a);
            Future<Integer> submitB = x.submit(b);
            System.out.println("submit...");

            while (true) {
                System.out.println("------------");
                System.out.println(submitA.isDone());
                System.out.println(submitB.isDone());
                Thread.sleep(1000L);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
