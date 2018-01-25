package com.jiangli.common.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @author Jiangli
 * @date 2018/1/19 9:11
 */
public class InetTest {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress host = InetAddress.getLocalHost();
        System.out.println(host.getHostName());
        System.out.println(host.getAddress());
        System.out.println(Arrays.toString(host.getAddress()));

        //64  01000000
        //192 11000000

        //40  00101000
        //168 10101000
        //88  01011000
        System.out.println(-64&0xff);
        System.out.println(-88&0xff);
        System.out.println((byte)168);
        System.out.println(192 & 0x7f);
        System.out.println(168 & 0x7f);
        System.out.println(-((~192&0x7f)+1));
        System.out.println(-((~168&0x7f)+1));
        System.out.println(~168);
        System.out.println(~70);
        System.out.println("-----------");

        //计算机数字有原码、反码、补码三种存储格式，通常都是补码(方便减运算)，java也不例外的使用补码。
        //补码：一个数如果为正，则它的原码、反码、补码相同；一个数如果为负，则符号位为1，其余各位是对原码取反，然后整个数加1。
        int a=2;
        System.out.println(~2); // 按位非（NOT）
        System.out.println(0x00000002); // 按位非（NOT）
        System.out.println(0x00000002); // 按位非（NOT）

        System.out.println(~a);
        System.out.println(~a + a);
        System.out.println(host.getHostAddress());
        System.out.println(host.getCanonicalHostName());
        //由于负数是用补码来表示的（求补码的方法是取反后加1，从补码转为原码也是取反后加1）
    //    把后面七位补码求源码=后面七位取反后加1
        System.out.println(~~2);
    }

}
