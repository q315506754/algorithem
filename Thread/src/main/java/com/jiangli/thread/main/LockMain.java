package com.jiangli.thread.main;

import java.util.ArrayList;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Jiangli
 * @date 2018/5/22 9:23
 */
public class LockMain {
    public static void main(String[] args) {
        System.out.println("aa");

        LockSupport.park(new ArrayList<>());
        LockSupport.park("asdsadsad");
        LockSupport.park();

        Object s = new Object();
        synchronized (s) {
            System.out.println("aaaa");
            try {
                s.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("finish");
    }

}
