package com.jiangli.test;

import com.jiangli.junit.MyThread;
import com.jiangli.junit.MyThreadJunitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Administrator
 *
 *         CreatedTime  2016/9/1 0001 16:38
 */
@RunWith(MyThreadJunitRunner.class)
public class JunitRunnerTest {
    int a = 100;

    @MyThread
    @Test
    public void func() {
        try {
            Thread.sleep(300);
            System.out.println("fffffffff");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @MyThread
    @Test
    public void func22() {
        System.out.println("fffffffff222");
    }

    @MyThread(threadNum = 100)
    @Test
    public void func3() {
       a++;
        System.out.println(a);
    }


}
