package com.jiangli.concurrent.util;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jiangli on 2016/7/9.
 */
public class ReentrantLockConditionTest {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        Condition condition2 = reentrantLock.newCondition();
        System.out.println(condition.getClass());
        System.out.println(condition);
        System.out.println(condition2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("running");
                    if (reentrantLock.tryLock()) {
                        System.out.println("thread tryLock1");
                        condition.signal();
                        condition2.signal();
                        System.out.println("release condition");
                        System.out.println("release condition2");
                        reentrantLock.unlock();
                    }else{
                        System.out.println("thread tryLock1 failed..");
                    }

//                    Thread.sleep(1000);

//                    if (reentrantLock.tryLock()) {
//                        System.out.println("thread tryLock2");
//                        condition2.signal();
//                        System.out.println("release condition2");
//                        reentrantLock.unlock();
//                    }else{
//                        System.out.println("thread tryLock2 failed..");
//                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        reentrantLock.tryLock();
        System.out.println("main tryLock");

        System.out.println("main waiting condition");
        condition.await();
        System.out.println("main get condition");
        System.out.println(reentrantLock.isHeldByCurrentThread());
        reentrantLock.unlock();


        System.out.println("main waiting condition2");
        condition2.await();
        System.out.println("main get condition2");

        System.out.println("over");
    }
}
