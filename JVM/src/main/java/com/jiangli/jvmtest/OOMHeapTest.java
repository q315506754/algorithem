package com.jiangli.jvmtest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiangli
 * @date 2019/5/24 9:17
 */
public class OOMHeapTest {
    /**
     *
     * 默认生成到项目路径
     * -Xms1m -Xmx1m -XX:+HeapDumpOnOutOfMemoryError  -XX:HeapDumpPath=C:\Users\Jiangli\Desktop -verbose:gc
     *
     *
     * java.lang.OutOfMemoryError: Java heap space
     * Dumping heap to java_pid20128.hprof ...
     * Heap dump file created [2694994 bytes in 0.011 secs]
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        List<Object> objects = new ArrayList<Object>();
        while (true) {
            objects.add(new OOMHeapTest());
        }
        //System.out.println("sd");
    }

}
