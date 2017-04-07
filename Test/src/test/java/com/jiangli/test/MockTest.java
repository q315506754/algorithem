package com.jiangli.test;

import org.junit.Assert;
import org.junit.Test;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/12 0012 10:53
 */
public class MockTest {

    @Test
    public void simpleTest(){

        //创建mock对象，参数可以是类，也可以是接口
        List<String> list = mock(List.class);

        //设置方法的预期返回值
        when(list.get(0)).thenReturn("helloworld");

        String result = list.get(0);

        //验证方法调用(是否调用了get(0))
        verify(list).get(0);

        //junit测试
        Assert.assertEquals("helloworld", result);

        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));
    }

    @Test
    public void argumentMatcherTest(){

        List<String> list = mock(List.class);

        when(list.get(anyInt())).thenReturn("hello","world");

        String result = list.get(0)+list.get(1);

        verify(list,times(2)).get(anyInt());

        Assert.assertEquals("helloworld", result);

        System.out.println(list.get(0));
        System.out.println(list.get(1));
        System.out.println(list.get(2));
        System.out.println(list.get(3));

    }

    interface A {
        default String a(int a) {
            String x = "a" + a;
            System.out.println(x);
            return x;
        }
        default void b(String b) {
            System.out.println("b"+b);
        }
    }

    @Test
    public void testObject() {
        A aIns = new A() {
        };
        System.out.println(aIns.a(12));

        A mock = mock(A.class);
        System.out.println(mock);

        String a = mock.a(12);
        System.out.println(a);

        when(mock.a(12)).thenReturn("gggggggaaa");

        a = mock.a(12);
        System.out.println(a);
    }
    @Test
    public void argumentMatcherTest2(){

        Map<Integer,String> map = mock(Map.class);
        when(map.put(anyInt(),anyString())).thenReturn("hello");//anyString()替换成"hello"就会报错
        map.put(1, "world");
        verify(map).put(eq(1), eq("world")); //eq("world")替换成"world"也会报错

    }

    @Test
    public void testSession(){
        HttpSession mock = mock(HttpSession.class);
        mock.setAttribute("ddd","xxx");
        System.out.println(mock.getAttribute("ddd"));

        when(mock.getAttribute("ddd")).thenReturn("hello");

        System.out.println(mock.getAttribute("ddd"));
    }

    @Test
    public void verifyInvocate(){

        List<String> mockedList = mock(List.class);
        //using mock
        mockedList.add("once");
        mockedList.add("twice");
        mockedList.add("twice");

        mockedList.add("three times");
        mockedList.add("three times");
        mockedList.add("three times");

        /**
         * 基本的验证方法
         * verify方法验证mock对象是否有没有调用mockedList.add("once")方法
         * 不关心其是否有返回值，如果没有调用测试失败。
         */
        verify(mockedList).add("once");
//        verify(mockedList).add("once23");
        verify(mockedList, times(1)).add("once");//默认调用一次,times(1)可以省略


        verify(mockedList, times(2)).add("twice");
        verify(mockedList, times(3)).add("three times");

        //never()等同于time(0),一次也没有调用
        verify(mockedList, times(0)).add("never happened");

        //atLeastOnece/atLeast()/atMost()
        verify(mockedList, atLeastOnce()).add("three times");
        verify(mockedList, atLeast(2)).add("twice");
        verify(mockedList, atMost(5)).add("three times");

    }
}
