package com.jiangli.bytecode.practice;

/**
 * @author Jiangli
 * @date 2019/11/26 11:18
 */
public class ASMHelper {

    public void func() {
        long lxxa = System.currentTimeMillis();


        //业务代码
        System.out.println("aaaa");

        long cost = System.currentTimeMillis() - lxxa;

        System.out.println("cost:"+cost+"ms");
    }

}
