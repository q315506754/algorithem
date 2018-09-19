package com.jiangli.thread.main.executors.collection.block;

import com.jiangli.thread.main.executors.ExecutorTestBase;

import java.util.concurrent.SynchronousQueue;

/**
 * @author Jiangli
 * @date 2018/9/18 15:37
 */
public class SynchronousQueueMain extends ExecutorTestBase {
    public static void main(String[] args) {
        //可设置公平策略 FIFO
        //相当于一个容量为0的阻塞队列  单个链结点
        SynchronousQueue synchronousQueue = new SynchronousQueue();

        System.out.println("start");
        try {
            new Thread(()->{
                while (true) {
                    sleep(2000);
                    try {
                        synchronousQueue.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            System.out.println(synchronousQueue.size());
            synchronousQueue.put("111");
            System.out.println("111");
            synchronousQueue.put("222");
            System.out.println("222");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
