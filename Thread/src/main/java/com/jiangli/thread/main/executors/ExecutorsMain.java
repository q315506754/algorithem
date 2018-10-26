package com.jiangli.thread.main.executors;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Jiangli
 * @date 2018/9/17 15:38
 */
public class ExecutorsMain {
    public static void main(String[] args) {
        System.out.println(Executors.class);
        System.out.println(TimeUnit.SECONDS.toMillis(1));
        System.out.println(TimeUnit.SECONDS.toMicros(1));
        System.out.println(TimeUnit.SECONDS.toNanos(1));
        LockSupport.parkNanos(new Object(), 3000000000L);//3s
        System.out.println("over...");
    }

}
