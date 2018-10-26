package com.jiangli.thread.main;

/**
 * @author Jiangli
 * @date 2018/9/28 9:20
 */
public class BitTest {
    /**
     *  原码：将一个整数转换成二进制表示
     2 的原码为：00000000 00000000 00000000 00000010
     -2的原码为：10000000 00000000 00000000 00000010

     正数的反码：与原码相同
     负数的反码：原码的符号位不变，其他位取反
     -2 的反码为：11111111 11111111 11111111 11111101

     正数的补码：与原码相同
     负数的补码：反码+1
     -2 的补码为：01111111 11111111 11111111 11111110

     正数的原码、反码、补码都一样；
     负数的反码 = 原码的符号位不变，其他位取反；
     负数的补码 = 反码+1；
     0的原码、反码、补码都是0；
     计算机以补码进行运算；
     取反不同于反码；

     */
    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(2));//整数打印出来以原码表示
        System.out.println(0b10000000_00000000_00000000_00000010);//-2147483646
        System.out.println(Integer.toBinaryString(-1));//
        System.out.println(Integer.toBinaryString(-2));//这里打印出来的为补码表示的值
        System.out.println(Integer.toBinaryString(-3));//      11111111111111111111111111111101
        System.out.println(Integer.toBinaryString(-3 << 1)); //11111111111111111111111111111010 低位补0
        System.out.println(Integer.toBinaryString(-3 >> 1)); //11111111111111111111111111111110 符号右移 补上符号位
        System.out.println(Integer.toBinaryString(-3 >>> 1));//01111111111111111111111111111110 无符号右移 高位通通补0。
        System.out.println(~2);//-3
        System.out.println(~10);//-11
        System.out.println(~18);//-19
        System.out.println(Integer.toBinaryString(~-2));//
        System.out.println(Integer.toBinaryString(~-2+1));//取反 + 1 = 2

        System.out.println(Integer.toBinaryString(~2));//非 取反

        //int x = 3;
        int x = -3;
        //正负转换
        System.out.println(~x+1==-x);//补码+1

        System.out.println(5^2);//7 异或 101 ^ 10
    }

}
