package com.jiangli.concurrent.util;

/**
 * Created by Jiangli on 2016/7/10.
 */
public class ThreadYieldTest {
    public static void main(String[] args) throws InterruptedException {
        Thread producer = new Producer();
        Thread consumer = new Consumer();

        producer.setPriority(Thread.MIN_PRIORITY); //Min Priority
        consumer.setPriority(Thread.MAX_PRIORITY); //Max Priority

        producer.start();
        consumer.start();
    }
    static class Producer extends Thread
    {
        public void run()
        {
            for (int i = 0; i < 5; i++)
            {
                System.out.println("I am Producer : Produced Item " + i);
                Thread.yield();
            }
        }
    }

    static class Consumer extends Thread
    {
        public void run()
        {
            for (int i = 0; i < 5; i++)
            {
                System.out.println("I am Consumer : Consumed Item " + i);
                Thread.yield();
            }
        }
    }
}
