package com.jiangli.datastructure.test;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jiangli
 * @date 2018/5/16 17:21
 */
public class CommonTest extends BaseTest {
    @Test
    public void test_() {
        System.out.println(Object[].class.getComponentType());
        System.out.println(Array.newInstance(Object.class, 3));
        System.out.println(Array.newInstance(String.class, 3));
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




}
