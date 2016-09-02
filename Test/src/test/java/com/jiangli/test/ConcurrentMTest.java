package com.jiangli.test;

import org.junit.Test;

import java.util.Vector;

/**
 * @author Administrator
 *
 *         CreatedTime  2016/9/1 0001 16:38
 */
public class ConcurrentMTest {

    @Test
    public void func() {

        Vector v = new Vector();

        int n = 10;
        while (n-- > 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        v.add("sdfsdfsdfsd"+System.currentTimeMillis());
                    }
                }
            }).start();
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (Object o : v) {
                    System.out.println(o);
                }
            }
        }).start();

        try {
            Thread.sleep(99999999);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
