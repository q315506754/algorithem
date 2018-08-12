package com.jiangli.concurrent.util;

import sun.misc.VM;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.atomic.AtomicLongFieldUpdater;

/**
 * @author Jiangli
 * @date 2018/7/10 10:24
 */
public class FieldUpdaterTest {
    static class Bod {
        private static  int AA =  100;
        protected  volatile long aa =  0;

        @CallerSensitive
        public  void funcX() {
            Class<?> callerClass = Reflection.getCallerClass();
            System.out.println(callerClass);
        }
    }


    @CallerSensitive
    public static void func() {
        Class<?> callerClass = Reflection.getCallerClass();
        System.out.println(callerClass);
    }

    public static void main(String[] args) throws NoSuchMethodException {
        Bod bod = new Bod();
        AtomicLongFieldUpdater<Bod> b = AtomicLongFieldUpdater.newUpdater(Bod.class, "aa");
        System.out.println(b.incrementAndGet(bod));
        System.out.println(bod.aa);

        try {
            AccessController.doPrivileged(new PrivilegedExceptionAction<Integer>() {
                public Integer run()   {
                    System.out.println("aaaa");
                    return 0;
                }
            });
        } catch (PrivilegedActionException e) {
            e.printStackTrace();
        }

        //Unsafe unsafe = Unsafe.getUnsafe();
        //System.out.println(unsafe);

        Method func = FieldUpdaterTest.class.getDeclaredMethod("func");
        System.out.println(func);
        System.out.println(Reflection.isCallerSensitive(func));
        System.out.println(Reflection.isCallerSensitive(Bod.class.getDeclaredMethod("funcX")));

        System.out.println(VM.getFinalRefCount());
        System.out.println(VM.maxDirectMemory());

        //func();

        Class<?> callerClass = Reflection.getCallerClass();
        //System.out.println(callerClass);

    }
}
