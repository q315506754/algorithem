package com.jiangli.bytecode;

/**
 *
 * 拆箱装箱机制
 * @author Jiangli
 * @date 2018/5/29 11:13
 */
public class Bytecode6Test {

    public static void main(String[] args) {
        int x = 333;
        Integer y = x;  //自动装箱 Integer.valueOf(x)
        y++;//自动拆箱 and 装箱
        System.out.println(y+10);//自动拆箱 y.intValue()

        //自动拆箱 y.intValue()
        if (y == 334) {
            System.out.println("==");
        }
        //自动装箱 Integer.valueOf(334)
        if (y.equals(334)) {
            System.out.println("equals");
        }
    }

}
