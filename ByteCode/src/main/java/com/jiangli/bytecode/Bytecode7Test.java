package com.jiangli.bytecode;

/**
 *
 *  同步
 *
 */
public class Bytecode7Test {

    public static void main(String[] args) {
        Object x = 1;

        //MONITORENTER
        synchronized (x) {
            System.out.println(x);

            try {
                x.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    //    MONITOREXIT

    }

}
