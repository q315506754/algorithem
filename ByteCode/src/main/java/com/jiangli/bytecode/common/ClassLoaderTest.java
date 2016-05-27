package com.jiangli.bytecode.common;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/5/27 0027 14:42
 */

public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ClassLoader cl = new ClassLoader() {
        };

//        Class<?> aClass = cl.loadClass("com.jiangli.bytecode.common.TestSnippet");


        HackClassLoader hcl = new HackClassLoader();


//        hcl.loadClass();

        byte[] bytes = ClassUtil.getBytes("com.jiangli.bytecode.common.TestSnippet");
        System.out.println(Arrays.toString(bytes));
//        System.out.println((int) bytes[0]);
//        System.out.println(bytes[0] & 0xff);
//        System.out.println((int) bytes[0] & 0xff);
//        System.out.println(((int) bytes[0]) & 0xff);
//        System.out.println((byte) 202);
//        System.out.println(ClassUtil.byteToHexString(bytes[0]));
//        System.out.println(Integer.toHexString(202));
//        System.out.println(Integer.toHexString(-2 & 0xff));
//        System.out.println(Integer.toHexString(-2 & 0xff));
//        byte b = 121;
//        byte b2 = -121;
//        System.out.println("------------------");
//        System.out.println(b);
//        System.out.println((int)b);
//        System.out.println((int)b&0xffff);
//        System.out.println((int)b&0xff);
//        System.out.println("------------------");
//        System.out.println(b2);
//        System.out.println((int)b2);
//        System.out.println((int)b2&0xffff);
//        System.out.println((int)b2&0xff);
//        System.out.println("------------------");

        String[] byteCharView = ClassUtil.byteCharView(bytes);
        System.out.println(Arrays.toString(byteCharView));
        int constantPoolCount = ClassUtil.bytesToInt(bytes, 8, 2);
        System.out.println(constantPoolCount);
        int startIdx = 10;
        int count = 0;
        int[] CONSTANT_ITEM_LENGTH = {-1,-1,-1,5,5,9,9,3,3,5,5,5,5};
        while (count ++ < constantPoolCount) {
            int tag = bytes[startIdx];
            int length = CONSTANT_ITEM_LENGTH[tag];
            //utf-8
            if (tag == 1) {
                int strLen  = ClassUtil.bytesToInt(bytes, startIdx+1, 2);
                length =  strLen + 3;
                String constantString = ClassUtil.bytesToString(bytes, startIdx + 3, strLen);
                System.out.println((count+1)+":"+constantString);
            } else {

            }

            startIdx +=  length;
        }


//        Class<?> aClass = hcl.loadClass("com.jiangli.bytecode.common.TestSnippet");
        Class<?> aClass = hcl.loadByte(bytes);

        testCl(aClass);
    }

    private static void testCl(Class<?> aClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Method test = aClass.getDeclaredMethod("test");
        test.invoke(aClass);
        System.out.println(TestClassA.i);
    }
}
