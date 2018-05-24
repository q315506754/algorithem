package com.jiangli.thread.main.lock;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Jiangli
 * @date 2018/5/22 9:58
 */
public class ReentrantLockMain {
    public static void main(String[] args) {
        //可重入锁
        //锁几次就要解锁几次，不然其它线程无法获得
        //通过参数构造公平、非公平锁，公平性指的是分配锁时是否考虑排队顺序
        //拥有类似synchronized块的功能，但功能更多，用完需要手动解锁
        ReentrantLock lock = new ReentrantLock();

        new Thread(() -> {
            lock.lock();
            //lock.lock();

            try {
                System.out.println("aa");
                //1纳秒=0.000000001秒=10^(-9)秒
                //
                //1s = 1000ms 毫秒
                //1ms = 1000μs 微秒
                //1μs = 1000ns 纳秒
                //1ns = 1000ps 皮秒
                //秒，分秒，毫秒，微秒，纳秒，皮秒，飞秒，仄秒，幺秒，渺秒
                LockSupport.parkNanos(3000000000L);
                System.out.println("aa wake");
            } finally {
                lock.unlock();

            }
        }).start();

        new Thread(() -> {
            lock.lock();
            //lock.lock();
            try {
                System.out.println("bb");
                LockSupport.parkNanos(3000000000L);
                System.out.println("bb wake");
            } finally {
                lock.unlock();

            }
        }).start();

    }
}
