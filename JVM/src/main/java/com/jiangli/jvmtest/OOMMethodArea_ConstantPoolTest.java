package com.jiangli.jvmtest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiangli
 * @date 2019/5/24 9:22
 */
public class OOMMethodArea_ConstantPoolTest {
    /**
     * -XX:PermSize=128k -XX:MaxPermSize=128k -verbose:gc
     *
     * -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m -verbose:gc
     * @param args
     */
    public static void main(String[] args) {
        List<String> objects = new ArrayList<>();
        int i = 0;
        while (true) {
            //jvm会回收常量池不用的常量 必须引用
            //("aa" + i++).intern();

            //首次复制永久代原则
            //jdk8没效 无法出现异常 似乎常量池满了后会把其它的赶出去，或是自身无法添加进去
            i++;
            String str = "test:"+i;
            objects.add(str.intern());
            //System.out.println(i);
        }
    }

}
