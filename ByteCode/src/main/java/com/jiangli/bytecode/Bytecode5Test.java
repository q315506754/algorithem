package com.jiangli.bytecode;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jiangli
 * @date 2018/5/29 11:13
 */
public class Bytecode5Test {
    private static int si;

    public static void main(String[] args) {
        System.out.println(si);
        String str=null;

        List<Integer> list = Arrays.asList(1, 2, 3);
        for (Integer integer : list) {
            str = String.valueOf(integer);
        }
        System.out.println(str);
        long aa;
        if (str!=null) {
            aa= 21;
        }else {
            aa= 3324324;
        }

        //2 slot
        System.out.println(aa);
        double xx = 333;
        System.out.println(xx);
        double xx2 = 333;
        System.out.println(xx);
        double xx3 = 333;
        System.out.println(xx);
    }

}
