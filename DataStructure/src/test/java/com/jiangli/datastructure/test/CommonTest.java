package com.jiangli.datastructure.test;

import org.junit.Assert;
import org.junit.Test;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jiangli
 * @date 2018/5/16 17:21
 */
public class CommonTest extends BaseTest {
    @Test
    public void test_11() {
        System.out.println("Hello world!");
    }
    
    public static void main(String[] args) {
        System.out.println("Hello world!");
    }
    @Test
    public void test_() {
        System.out.println(Object[].class.getComponentType());
        System.out.println(Array.newInstance(Object.class, 3));
        System.out.println(Array.newInstance(String.class, 3));
    }

    @Test
    public void test_234() {
        System.out.println("ip:+"+null);
    }


    @Test
    public void test_local_destop() throws IOException {
        System.out.println(Arrays.toString(File.listRoots()));
        System.out.println(File.createTempFile("aabb",""));
        System.out.println(File.createTempFile("aabb",""));

        FileSystemView fsv = FileSystemView.getFileSystemView();
        File com=fsv.getHomeDirectory();    //这便是读取桌面路径的方法了
        System.out.println(com.getPath());

        System.out.println(fsv.getDefaultDirectory());

    }

    @Test
    public void test_23() {
        List<String> objects = new ArrayList<>();
        //List<String> objects = new CopyOnWriteArrayList<>();
        objects.add("aa");
        objects.add("bb");

        for (String object : objects) {
            System.out.println(object);
            objects.add("12");
        }
    }


    @Test
    public void test_34() {
       System.out.println(Runtime.getRuntime().availableProcessors());
    }

    @Test
    public void test_12() {
        System.out.println(8 << 1);//x2
        System.out.println(8 << 2);//x4
        System.out.println(8 << 3);//x8
        //[-54, -2, -70, -66]
        System.out.println(Integer.toHexString(-54 & 0xff << 3));//
        System.out.println(Integer.toHexString(-2 & 0xff << 2));//
        System.out.println(Integer.toHexString(-70 & 0xff << 1));//
        System.out.println(Integer.toHexString(-66 & 0xff << 0));//
    }

    @Test
    public void test_cmp() {
        System.out.println("TRUE".equalsIgnoreCase("tRuE"));
    }


    @Test
    public void test_2() {
        System.out.println(1 >>> 1);
        System.out.println(2 >>> 1);
    }

    @Test
    public void test_3() {
        System.out.println((Object) null);
        System.out.println(null +"");

        String[] ar = new String[]{""};
        for (String o : ar){
            System.out.println(o);
        }

        List<String> strings = Arrays.asList("", "");
        for (String o : strings){
            System.out.println(o);
        }

    }

    @Test
    public void test_4() {
        String[] a = new String[10];
        for (int i = 0; i < 10; i++) {
            a[i] = i + "";
        }
        System.out.println(Arrays.toString(a));

        //System.arraycopy(a,2,a,0,8);
        //arraycopy(a,2,a,0,8);
        //System.arraycopy(a,0,a,2,8);
        System.arraycopy(a,0,a,1,8);
        //arraycopy(a,0,a,1,8);
        System.out.println(Arrays.toString(a));
    }

    public void arraycopy(String[] src,  int  srcPos,
                          String[] dest, int destPos,
                          int length) {
        for (int i = 0; i < length; i++) {
            dest[destPos+i] = src[srcPos+i];
        }

    }



    @Test
    public void test_5() {
        int cap = 0b0101;

        int n = cap - 1;
        Assert.assertEquals(n,0b0100);

        n |= n >>> 1;
        Assert.assertEquals(n,0b0110);

        n |= n >>> 2;
        Assert.assertEquals(n,0b0111);

        n |= n >>> 4;
        Assert.assertEquals(n,0b0111);

        n |= n >>> 8;
        Assert.assertEquals(n,0b0111);

        n |= n >>> 16;
        Assert.assertEquals(n,0b0111);

        System.out.println(n);
        System.out.println(0b0111 >>> 16);//0
        System.out.println(0b0111 >> 16);//0

        int MAXIMUM_CAPACITY = Integer.MAX_VALUE;
        int y = (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
        System.out.println(y);
    }

    @Test
    public void test_6() {
        System.out.println(tableSizeFor(0b00000001));
        System.out.println(tableSizeFor(0b00000011));
        System.out.println(tableSizeFor(0b00000111));
        System.out.println(tableSizeFor(0b00001111));
        System.out.println(tableSizeFor(0b00011111));
        System.out.println(tableSizeFor(0b00111111));
        System.out.println(tableSizeFor(0b01111111));
        System.out.println(tableSizeFor(0b11111111));
    }

    @Test
    public void test_7() {
        System.out.println(0b111 ^ 0b010);//101 异或
    }


    @Test
    public void test_8()
    {
        System.out.println(2323/0);
    }

    @Test
    public void test_9() {
        System.out.println(null instanceof Object);
        System.out.println(null instanceof String);
    }

    @Test
    public void test_10() {
        System.out.println(-1 << 1);//-2
        System.out.println(-1 >> 1);//-1
        System.out.println(-1 >>> 1);//2147483647
        System.out.println(Integer.MAX_VALUE);//2147483647

        System.out.println(-Integer.MAX_VALUE >>> 1);
        System.out.println(Integer.toHexString(-Integer.MAX_VALUE));
        System.out.println(Integer.toHexString(-Integer.MAX_VALUE>>>1));
        System.out.println(Integer.MIN_VALUE << 1);//0
        System.out.println(Integer.MIN_VALUE-1 << 1);//-2
        //System.out.println(-1012345 >>> 1);//-2

        System.out.println(-2 >> 1);//-1
        System.out.println(-8 >> 1);//-4 1000
        System.out.println(-8 >>> 1);//-4 2147483644

        System.out.println(-1 << 32);//-1
        System.out.println(-1 << 33);//-2
    }




    static final int tableSizeFor(int cap) {
        //是的

        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= Integer.MAX_VALUE) ? Integer.MAX_VALUE : n + 1;
    }

}
