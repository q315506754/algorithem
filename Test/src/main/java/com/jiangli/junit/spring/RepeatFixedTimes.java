package com.jiangli.junit.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 执行@Test 固定次数
 *
 * @author Jiangli
 * @date 2017/11/17 9:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RepeatFixedTimes {
    int value() default 1;//单位 次数
    boolean printDetail() default false; //打印每一次执行结果
}
