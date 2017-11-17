package com.jiangli.common.test;

import org.junit.Test;

/**
 * Created by Jiangli on 2017/4/20.
 */
public class SystemTest {

    @Test
    public void test_printf() {
        double d = 345.678;
        String s = "hello!";
        int i = 1234;
//"%"表示进行格式化输出，"%"之后的内容为格式的定义。
        System.out.printf("%f",d);//"f"表示格式化输出浮点数。
        System.out.printf("%9.2f",d);//"9.2"中的9表示输出的长度，2表示小数点后的位数。
        System.out.printf("%+9.2f",d);//"+"表示输出的数带正负号。
        System.out.printf("%-9.4f",d);//"-"表示输出的数左对齐（默认为右对齐）。
        System.out.printf("%+-9.3f",d);//"+-"表示输出的数带正负号且左对齐。
        System.out.printf("%d",i);//"d"表示输出十进制整数。
        System.out.printf("%o",i);//"o"表示输出八进制整数。
        System.out.printf("%x",i);//"d"表示输出十六进制整数。
        System.out.printf("%#x",i);//"d"表示输出带有十六进制标志的整数。
        System.out.printf("%s",s);//"d"表示输出字符串。
        System.out.printf("输出一个浮点数：%f，一个整数：%d，一个字符串：%s",d,i,s);//可以输出多个变量，注意顺序。
        System.out.printf("字符串：%2$s，%1$d的十六进制数：%1$#x",i,s);//"X$"表示第几个变量。
        System.getProperties().keySet().forEach(o -> System.out.println(o+" = " +System.getProperty(o.toString())));
    }
}
