package com.jiangli.jdk.v1_7;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/23 0023 14:19
 */
public class GrammaTest {
    public static void main(String[] args) {
        int one_million = 1_000_000;
        System.out.println(one_million);
        int binary = 0b1001_1001;
        System.out.println(binary);
        byte aByte = (byte)0b001;
        System.out.println(aByte);
        short aShort = (short)0b010;
        System.out.println(aShort);

        String s = "test";
        switch (s) {
            case "test" :
                System.out.println("test");
            case "test1" :
                System.out.println("test1");
                break ;
            default :
                System.out.println("break");
                break ;
        }


        System.out.println(System.getProperties());
    }

}
