/*
 * Decompiled with CFR.
 */
package com.jiangli.bytecode;

import java.io.PrintStream;
import java.io.Serializable;
import org.springframework.beans.factory.annotation.Autowired;

public class ByteDto
implements Runnable,
Serializable {
    @Autowired
    private Integer integer;
    protected String s = "aa";
    private static double aDouble;
    public static String str;

    static {
        str = "ABCD";
    }

    @Override
    public void run() {
        System.out.println("hello" + str);
    }
}

