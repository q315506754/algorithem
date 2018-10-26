package com.jiangli.thread.main.executors;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author Jiangli
 * @date 2018/9/17 15:02
 */
public class ExecutorInvokeAny extends ExecutorTestBase {
    public static void main(String[] args) {
        ExecutorService x = getExecutorService();
        System.out.println(x);

        try {
            //Object o = x.invokeAny(getCallableList());
            Object o = x.invokeAny(getCallableList(),3, TimeUnit.SECONDS); //到时间会执行 TimeoutException
            System.out.println(o);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }


}
