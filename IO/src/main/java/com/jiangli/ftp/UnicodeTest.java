package com.jiangli.ftp;

import java.util.Arrays;

/**
 * @author Jiangli
 * @date 2020/8/11 14:26
 */
public class UnicodeTest {

    public static void main(String[] args) throws Exception {
        String s = "STOR 开发日志.txt\r\n";
        System.out.println(Arrays.toString(s.getBytes("utf8")));
        System.out.println(Arrays.toString(s.getBytes("gbk")));
        System.out.println(Arrays.toString("志".getBytes("utf8")));
        System.out.println(Arrays.toString(".".getBytes("utf8")));

        byte[] bytes = new byte[]{83, 84, 79, 82, 32, -27, -68, -128, -27, -113, -111, -26, -105, -91, -27, -65, -105, 46, 116, 120, 116, 13, 10};
        System.out.println(new String(bytes));
        bytes = new byte[]{83, 84, 79, 82, 32, -27, -68, -128, -27, -113, -111, -26, -105, -91, -27, -65,63,116,120,116};
        System.out.println(new String(bytes));
    //    [83, 84, 79, 82, 32, -27, -68, -128, -27, -113, -111, -26, -105, -91, -27, -65, -105, 46, 116, 120, 116, 13, 10]
        //[83, 84, 79, 82, 32, -65, -86, -73, -94, -56, -43, -42, -66, 46, 116, 120, 116, 13, 10]
    }
}
