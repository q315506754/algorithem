package com.jiangli.concurrent.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Jiangli on 2016/7/9.
 */
public class ReentrantLockTest {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        Condition condition = reentrantLock.newCondition();
        System.out.println(condition.getClass());
        System.out.println(reentrantLock.isHeldByCurrentThread());
//        reentrantLock.lock();
//        reentrantLock.lock();
        System.out.println(reentrantLock.isHeldByCurrentThread());


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    System.out.println("sud thread running..");
                    if(reentrantLock.tryLock(3000, TimeUnit.MILLISECONDS)){
                        System.out.println("thread tryLock1");
                        if(reentrantLock.tryLock()){
                            System.out.println("thread tryLock2");
                        }
                    }else{
                        System.out.println("thread tryLock1 failed..");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        if(reentrantLock.tryLock()){
            System.out.println("tryLock1");
            if(reentrantLock.tryLock()){
                System.out.println("tryLock2");
            }
        }
        System.out.println("over");
    }
}
