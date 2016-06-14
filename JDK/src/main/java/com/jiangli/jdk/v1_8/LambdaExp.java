package com.jiangli.jdk.v1_8;

import java.util.Collections;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/14 0014 17:24
 */
public class LambdaExp {
    public static void main(String[] args) {
        new Thread(()->System.out.println("haha")).start();

        CommonInfv1_8 commonInfv1_8 = (String a) ->{
            return "function called:"+a;
        };
        System.out.println(commonInfv1_8.function("JL"));
        commonInfv1_8 = (a) ->{
            return "function2 called:"+a;
        };
        System.out.println(commonInfv1_8.function("JL"));

        commonInfv1_8 = (a) -> "function3 called:"+a;
        System.out.println(commonInfv1_8.function("JL"));

        commonInfv1_8 = a -> "function4 called:"+a;
        System.out.println(commonInfv1_8.function("JL"));

        Collections.sort(null, (String a, String b) -> b.compareTo(a));//right
//        Collections.sort(null,  (a,b) -> b.compareTo(a));//x
        commonInfv1_8.print("guagua");
    }
}
