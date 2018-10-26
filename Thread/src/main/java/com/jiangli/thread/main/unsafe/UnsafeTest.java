package com.jiangli.thread.main.unsafe;

import sun.misc.Unsafe;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

import java.lang.reflect.Field;

/**
 * @author Jiangli
 * @date 2018/9/17 15:52
 */
public class UnsafeTest {
    private static final Unsafe UNSAFE = getUnsafe();

    @CallerSensitive
    public static void func() {
        Class<?> callerClass = Reflection.getCallerClass();
        System.out.println(callerClass);
    }

    static class A{
        protected int grade;
        protected String department;

        public int getGrade() {
            return grade;
        }

        public A setGrade(int grade) {
            this.grade = grade;
            return this;
        }

        public String getDepartment() {
            return department;
        }

        public A setDepartment(String department) {
            this.department = department;
            return this;
        }
    }

    public static Unsafe getUnsafe() {
        try {
            //sun.misc.Unsafe
            Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnsafe.setAccessible(true);
            return (Unsafe) theUnsafe.get(null);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //Class<?> callerClass = Reflection.getCallerClass();
        //System.out.println(callerClass);
        return null;
    }

    public static void main(String[] args) throws NoSuchFieldException {
        //func();

        A student = new A();

        long grade = UNSAFE.objectFieldOffset(A.class.getDeclaredField("grade"));
        long department = UNSAFE.objectFieldOffset(A.class.getDeclaredField("department"));
        System.out.println(grade);
        System.out.println(department);

        student.setGrade(1);
        System.out.println("before："+student.grade);
        System.out.println(UNSAFE.compareAndSwapInt(student,grade,1,2));
        System.out.println("after："+student.grade);
    }
}
