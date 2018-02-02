package com.jiangli.common.test;

import org.junit.Test;

/**
 * Created by Jiangli on 2016/8/5.
 */
public class LoopTest {

    /*
    import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class TracingScript {
    @OnMethod(clazz="com.jiangli.common.test.LoopTest",method="add",location=@Location(Kind.RETURN))
    public static void execute(@Return int rtn,int n) {
        println(strcat("rs:",str(rtn)));
    }
}

     */


    @Test
    public void test_() {
        for (int pageIndex = 0; pageIndex < 1; pageIndex++) {
            System.out.println("sd");
        }
    }




    @Test
    public void func() {
        int n = 0;
        while (true) {
            n= add(n);
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int add(int n) {
        return ++n;

    }
}
