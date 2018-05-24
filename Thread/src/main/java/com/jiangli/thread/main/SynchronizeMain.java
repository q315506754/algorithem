package com.jiangli.thread.main;

/**
 * @author Jiangli
 * @date 2018/5/21 11:17
 */
public class SynchronizeMain {
    public static void main(String[] args) {

        synchronized (SynchronizeMain.class) {
            System.out.println("lock1");
            synchronized (SynchronizeMain.class) {
                System.out.println("lock2");
            }
        }

    }

}
