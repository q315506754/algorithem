package com.jiangli.leetcode.test.practice;

import com.jiangli.junit.spring.StatisticsJunitRunner;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author Jiangli
 * @date 2018/9/18 16:55
 */
@RunWith(StatisticsJunitRunner.class)
@Ignore
public class PracticeBase {
    protected List<InAndOutData> data = new ArrayList<>();
    public static ThreadLocal<Boolean> bl = new ThreadLocal<>();

    public static void shouldPrint(boolean v) {
       bl.set(v);
    }
    public static boolean shouldPrint() {
       return bl.get()==null || bl.get();
    }

    public static class InAndOutData {
        public Object[] params;
        public Object[] expected;
    }

    public  void validate(Function<Object[],Object[]> consumer) {
        for (InAndOutData datum : data) {
            Object[] apply = consumer.apply(datum.params);
            Assert.assertTrue(String.format("a1%s!=a2%s",Arrays.toString(apply),Arrays.toString(datum.expected)), Arrays.equals(apply,datum.expected));
        }
    }

    public static int[] a(int... args) {
        if (args==null) {
            return new int[0];
        }
        return args;
    }

    public static void ats(int... args) {
        System.out.println(Arrays.toString(args));
    }

    public static void ae(int[] a1,int[] a2) {
        Assert.assertTrue(String.format("a1%s!=a2%s",Arrays.toString(a1),Arrays.toString(a2)), Arrays.equals(a1,a2));
    }

    public static <T> void ae(T input, Function<T, T> consumer, T... expected) {
        T output = consumer.apply(input);
        boolean eq = false;
        if (expected != null  ) {
            if (expected.length == 1) {
                eq = output.equals(expected[0]);
            }else {
                if (output!=null) {
                    for (T t : expected) {
                        if (t.equals(output)) {
                            eq =  true;
                            break;
                        }
                    }
                }
            }
        } else {
            eq = output == null;
        }

        String s = null;
        if (expected!=null ) {
            if (expected.length == 1) {
                s = String.valueOf(expected[0]);
            }else  if (expected.length > 1) {
                s = Arrays.toString(expected);
            }
        }

        String format;
        if (expected== null || expected.length <= 1) {
            format = String.format("param:%s actual:%s expected:%s", input,String.valueOf(output), s);
        } else {
             format = String.format("param:%s actual:%s expected in:%s", input,String.valueOf(output), s);
        }

        Assert.assertTrue(format, eq);

        if (eq && shouldPrint()) {
            System.out.println(format);
        }

    }
    public static <T> void ae(T input, T input2, BiFunction<T, T,T> consumer, T... expected) {
        T output = consumer.apply(input,input2);
        boolean eq = false;
        if (expected != null  ) {
            if (expected.length == 1) {
                eq = output.equals(expected[0]);
            }else {
                if (output!=null) {
                    for (T t : expected) {
                        if (t.equals(output)) {
                            eq =  true;
                            break;
                        }
                    }
                }
            }
        } else {
            eq = output == null;
        }

        String s = null;
        if (expected!=null ) {
            if (expected.length == 1) {
                s = String.valueOf(expected[0]);
            }else  if (expected.length > 1) {
                s = Arrays.toString(expected);
            }
        }

        String format;
        if (expected== null || expected.length <= 1) {
            format = String.format("param:%s actual:%s expected:%s", input,String.valueOf(output), s);
        } else {
             format = String.format("param:%s actual:%s expected in:%s", input,String.valueOf(output), s);
        }

        Assert.assertTrue(format, eq);

        if (eq && shouldPrint()) {
            System.out.println(format);
        }

    }
}
