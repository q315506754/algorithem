package com.jiangli.jvmlanguage.javap;

/**
 * @author Jiangli
 * @date 2018/5/17 10:57
 */
public class YoungGCTest {
    //-Xmn10m -Xms20m -Xmx20m -XX:+PrintGCDetails
    public static void main(String[] args) {
        byte[] allocate = allocate(2);
        byte[] allocate2 = allocate(2);
        byte[] allocate3 = allocate(2);
        byte[] allocate4 = allocate(4);
        //byte[] allocate5 = allocate(40);
        System.out.println("hello"+null);
        //System.out.println(allocate);
        //System.out.println(allocate2);
        //System.out.println(allocate3);
        //System.out.println(allocate4);
    }

    private static byte[] allocate(int n) {
        return new byte[n * 1024 * 1024];
    }
}
