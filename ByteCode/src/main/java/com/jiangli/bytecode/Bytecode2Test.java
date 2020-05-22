package com.jiangli.bytecode;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jiangli
 * @date 2018/5/29 11:13
 */
public class Bytecode2Test {
    public static void main(String[] args) {
        String str=null;

        List<Integer> list = Arrays.asList(1, 2, 3);
        for (Integer integer : list) {
            str = String.valueOf(integer);
        }
        System.out.println(str);


        Integer a = 122222;
        int x = a;

        int mod =a%23;//IREM
        System.out.println(mod);
         mod =a/23;//IDIV
        System.out.println(mod);
         mod =a*23;//IMUL
        System.out.println(mod);
         mod =a+23;//IADD
        System.out.println(mod);
         mod =a-23;//ISUB
        System.out.println(mod);
         mod =a&23;//IAND
        System.out.println(mod);
         mod =a^23;//IXOR
        System.out.println(mod);
    }

}
