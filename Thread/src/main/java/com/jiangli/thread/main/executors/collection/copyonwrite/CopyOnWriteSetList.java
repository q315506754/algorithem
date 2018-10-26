package com.jiangli.thread.main.executors.collection.copyonwrite;

import java.lang.reflect.Field;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Jiangli
 * @date 2018/9/25 13:31
 */
public class CopyOnWriteSetList {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        CopyOnWriteArrayList<Object> es = new CopyOnWriteArrayList<>();
        Field array = CopyOnWriteArrayList.class.getDeclaredField("array");
        array.setAccessible(true);

        es.add(new Object());
        System.out.println(array.get(es));

        es.add(new Object());
        System.out.println(array.get(es));
    //    每add一次 换了一个数组 新数组长度为老鼠组+1  初始时为0




    }
}
