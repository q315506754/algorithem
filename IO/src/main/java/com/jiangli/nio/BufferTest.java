package com.jiangli.nio;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/23 0023 15:36
 */
public class BufferTest {
    static int i = 1;
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ByteBuffer allocate = ByteBuffer.allocate(1024);
        System.out.println(BeanUtils.describe(allocate));

        print(allocate);
        allocate.put((byte)12);
        allocate.put((byte)12);
        allocate.put((byte)12);
        print(allocate);

        allocate.mark();
        allocate.put((byte) 23);
        allocate.put((byte) 34);
        allocate.put((byte)45);
        print(allocate);

        allocate.reset();
        print(allocate);

        Buffer flip = allocate.flip();
        print(allocate);

        System.out.println("sss:"+new String(((ByteBuffer)flip).array(),0,flip.limit())+":eee");

    }

    public static void print(ByteBuffer allocate) {
        System.out.println("--------"+(i++)+"---------");
        System.out.println("position:"+allocate.position());
        System.out.println("limit:"+allocate.limit());
        System.out.println("capacity:"+allocate.capacity());
    }

}
