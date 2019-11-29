package com.jiangli.bytecode;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * watch -h
 * watch com.jiangli.bytecode.ByteDto run #cost
 *  watch com.jiangli.bytecode.ByteDto run "{params,returnObj}"
 * redefine C:\\myprojects\\algorithem\\ByteCode\\target\\classes\\com\\jiangli\\bytecode\\ByteDto.class
 *
 * dump会导致redefine失效
 * dump *ByteDto* C:\\myprojects\\arthas
 *
 *
 * monitor -c 1 com.jiangli.bytecode.ByteDto run
 * @author Jiangli
 * @date 2019/11/26 9:38
 */
public class ByteDto implements Runnable, Serializable {
    @Autowired
    private Integer integer;
    protected String s="aa";
    private static double aDouble;
    public static String str = "ABCD";

    @Override
    public void run() {
        System.out.println("hello22 " + str);
    }
}
