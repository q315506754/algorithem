package com.jiangli.common.test;

/**
 * 控制台颜色
 *
 * @author Jiangli
 * @date 2018/11/20 14:37
 */
public class ColorTest {
    public static void main(String[] args) {
        System.out.println("\033[31mRed Text\033[0m");
        System.out.println("\033[32mGreen Text\033[0m");
        System.out.println("\033[4mGreen Text\033[0m");
    }

}
