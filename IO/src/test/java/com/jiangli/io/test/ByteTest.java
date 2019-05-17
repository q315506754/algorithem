package com.jiangli.io.test;

import org.junit.Test;

import java.util.Arrays;

/**
 * @author Jiangli
 * @date 2019/5/15 10:32
 */
public class ByteTest {
    @Test
    public void test_() {
        byte[] bytes = new byte[]{-54, -2, -70, -66};
        System.out.println(Arrays.toString(bytes));
        System.out.println((int)bytes[0]);//-54
        System.out.println(bytes[0]&0xff);//202
        System.out.println((byte)202);//-54
        System.out.println((char)bytes[0]);//ￊ
        System.out.println((char)99);//c

        System.out.println(Integer.toHexString(bytes[0]&0xff)+" " + Integer.toBinaryString(bytes[0]&0xff));//ca
        System.out.println(Integer.toHexString(bytes[1]&0xff)+" " + Integer.toBinaryString(bytes[1]&0xff));//fe
        System.out.println(Integer.toHexString(bytes[2]&0xff)+" " + Integer.toBinaryString(bytes[2]&0xff));//ba
        System.out.println(Integer.toHexString(bytes[3]&0xff)+" " + Integer.toBinaryString(bytes[3]&0xff));//be

        //返回的字符串长度跟数字大小有关
        System.out.println(Integer.toHexString(123213));//1e14d
        System.out.println(Integer.toHexString(202));//ca
        System.out.println(Integer.toBinaryString(21344));//101001101100000
        System.out.println(Integer.toBinaryString(0xff));//
        System.out.println(Integer.toBinaryString(0xff << 1));//
    }

}
