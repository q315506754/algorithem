package com.jiangli.thread.main;

/**
 * @author Jiangli
 * @date 2018/9/18 16:46
 */
public class ObjTest {
    public static void main(String[] args) {
        Object o = new Object();
        synchronized (o) {
            try {
                o.wait(3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
