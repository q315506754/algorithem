package com.jiangli.nio;

import sun.misc.VM;

/**
 * @author Jiangli
 * @date 2017/10/19 10:23
 */
public class VmTest {
    public static void main(String[] args) {
        //没有配置MaxDirectMemorySize的，因此MaxDirectMemorySize的大小即等于-Xmx  默认1897922560 约1.767578125G

        //当配置了-Xmx1280k 则打印1572864=1536k  1.2倍

//        -XX:MaxDirectMemorySize=111k  指定后打印 113664=111k
        System.out.println(VM.maxDirectMemory());

        //避免-Xmx + Direct Memory超出物理内存大小的现象。
    }

}
