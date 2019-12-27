package com.jiangli.bytecode;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**

 Search-Class sc *ByteDto*
 sc -d *ByteDto*
 sc com.jiangli.*

 Search-Method sm com.jiangli.* *run*
 sm java.lang.String

 jad com.jiangli.bytecode.ByteDto

 redefine C:\\myprojects\\algorithem\\ByteCode\\target\\classes\\com\\jiangli\\bytecode\\ByteDto.class

 dump、jad 会导致redefine失效
 dump *ByteDto*

 classloader
 classloader -t
 classloader -l
 classloader -c 18b4aac2  -r META-INF/MANIFEST.MF | grep spring


 ----------- 监控 --------------

 monitor -c 2 com.jiangli.bytecode.ByteDto run

 watch -h
 watch com.jiangli.bytecode.ByteDto run #cost
 watch com.jiangli.bytecode.ByteDto run "{params,returnObj}" -x 2
 watch com.jiangli.bytecode.ByteDto run "{target}" -x 2 -n 5
 watch com.jiangli.bytecode.ByteDto run "{target.integer}" -x 2 -n 5

 只能trace 1层 因为代价昂贵
 trace com.jiangli.bytecode.ByteDto run -n 5
 trace com.jiangli.bytecode.ByteDto run --skipJDKMethod false -n 5
 trace com.jiangli.bytecode.ByteDto run '#cost > 10' --skipJDKMethod false -n 5
 trace多层效果
 trace com.jiangli.bytecode.ByteDto run*
 trace com.jiangli.bytecode.ByteDto run* --skipJDKMethod false

 stack  com.jiangli.bytecode.ByteDto run
 stack  com.jiangli.bytecode.ByteDto run3

 time tunnel

 tt -t com.jiangli.bytecode.ByteDto run*
 tt -l
 tt -i 1101
 tt -i 1101 -p --replay-times 2 --replay-interval 2000

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
        run2();
    }

    public void run2() {
        System.out.println("run2.... " + str);
        run3();
    }

    public void run3() {
        System.out.println("run3.... " + str);
    }
}
