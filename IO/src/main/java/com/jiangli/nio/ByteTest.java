package com.jiangli.nio;

/**
 * @author Jiangli
 * @date 2020/5/30 19:27
 */
public class ByteTest {
    public static void main(String[] args) {
        byte x = -54;
        System.out.println(x);

        int z = x;
        //byte -> int
        System.out.println(0xff&x);
        System.out.println(z);
        System.out.println(0xff);

        //-54
        // int -> byte 可能为负
        byte y = (byte) 202;
        System.out.println(y);

        System.out.println(Integer.toHexString(x));

        System.out.println((byte)-54);
        System.out.println(Integer.toBinaryString(-54));
        System.out.println(Integer.toBinaryString(-54&0xff));

    }
}
