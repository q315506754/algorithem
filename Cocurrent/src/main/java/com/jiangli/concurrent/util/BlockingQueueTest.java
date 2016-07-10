package com.jiangli.concurrent.util;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Jiangli on 2016/7/8.
 */
public class BlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<String> queue = new ArrayBlockingQueue<String>(20);
        queue.put("1111");
        queue.put("2222");

        ArrayList<String> c = new ArrayList<>();
        queue.drainTo(c);

        System.out.println(c);
    }
}
