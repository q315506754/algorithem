package com.jiangli.thread.main.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 *
 * 写的时候不能读
 * 写的时候不能写
 *
 * 读的时候不能写 (导致写效率低下)
 * 读的时候可以读
 *
 *
 * @author Jiangli
 * @date 2018/5/22 20:43
 */
public class ReentrantReadWriteLockMain {
    public static void main(String[] args) {
        //也分为公平非公平版本
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();

        new Thread(()->{
            while (true) {
                System.out.println("准备读取数据");
                readLock.lock();
                System.out.println("已读到数据");
                readLock.unlock();

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(()->{
            System.out.println("尝试写入数据");
           writeLock.lock();

           try {
               System.out.println("写入数据....");
               Thread.sleep(3000);

               System.out.println("已写完....");
           } catch (InterruptedException e) {
               e.printStackTrace();
           }

            writeLock.unlock();
        }).start();
    }

}
