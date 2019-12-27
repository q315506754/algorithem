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
    }

}
