package com.jiangli.bytecode.decompile;

/**
 * Created by Jiangli on 2016/7/10.
 */
public class ArrayDec {
    /**
     *
     ICONST_5
     NEWARRAY T_INT
     ASTORE 1

     ALOAD 1
     ASTORE 2
     ALOAD 2
     ARRAYLENGTH
     ISTORE 3
     ICONST_0
     ISTORE 4

     ILOAD 4
     ILOAD 3
     IF_ICMPGE L3
     ALOAD 2
     ILOAD 4
     IALOAD
     ISTORE 5

     GETSTATIC java/lang/System.out : Ljava/io/PrintStream;
     ILOAD 5
     INVOKEVIRTUAL java/io/PrintStream.println (I)V

     IINC 4 1
     GOTO L2

     RETURN

     * @param args
     */
    public static void main(String[] args) {
        int[] arr = new int[5];
        for (int i : arr) {
            System.out.println(i);
        }
    }
}
