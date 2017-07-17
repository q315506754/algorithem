package com.jiangli.common.utils;

/**
 * @author Jiangli
 * @date 2017/7/17 16:20
 */
public class ArgorithemUtil {
    /**
     * O(1)
     *
     * 若为2的乘方,其二进制必然
     * 10
     * 100
     * 1000
     * 10000....
     *
     * 则乘方-1=
     * 1
     * 11
     * 111
     * 1111
     * 11111
     *
     * 故N & (N-1) = 0  相同位为0
     * @param a
     * @return
     */
    public static boolean isPowerOf2(int a) {
        return (a & a -1) == 0;
    }

    /**
     * 二进制中1的位数
     *
     * @param a
     * @return
     */
    public static int numOf1InBinary(int a) {
        return (a & a -1) == 0;
    }
}
