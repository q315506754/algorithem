package com.jiangli.bytecode.decompile;

/**
 * Created by Jiangli on 2016/7/10.
 */
public class SyncDec {
    /*
    LDC Lcom/jiangli/bytecode/decompile/SyncDec;.class
    DUP
    ASTORE 1
    MONITORENTER

    GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
    LDC "aaaaaa"
    INVOKEVIRTUAL java/io/PrintStream.println (Ljava/lang/String;)V

    ALOAD 1
    MONITOREXIT

     */
    public static void main(String[] args) {
        synchronized (SyncDec.class) {
            System.out.println("aaaaaa");
        }
    }
}
