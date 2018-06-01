package com.jiangli.bytecode;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jiangli
 * @date 2018/5/29 11:13
 */
public class Bytecode4Test {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3);
        for (Integer integer : list) {
            String str = String.valueOf(integer);
            System.out.println(str);
        }
        for (Integer integer : list) {
            String str1 = String.valueOf(integer);
            System.out.println(str1);
        }
        for (Integer integer : list) {
            String str = String.valueOf(integer);
            System.out.println(str);
        }
        for (Integer integer : list) {
            String str = String.valueOf(integer);
            System.out.println(str);
        }
        //list.forEach(integer -> {
        //    String str2 = String.valueOf(integer);
        //    System.out.println(str2);
        //});
    }

}
