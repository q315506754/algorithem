package com.jiangli.leetcode.test.practice;

import com.jiangli.junit.spring.StatisticsJunitRunner;
import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author Jiangli
 * @date 2018/9/18 16:55
 */
@RunWith(StatisticsJunitRunner.class)
@Ignore
public class PracticeBase {
    protected List<InAndOutData> data = new ArrayList<>();

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
}
