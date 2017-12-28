package com.jiangli.junit.spring.data.handler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * 配置怎么处理数据
 *
 * @author Jiangli
 * @date 2017/11/17 9:12
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ResultsCollector {
    Class<?extends DataHandler> value() default ConsolePrintDataHandler.class;

    boolean printDetail() default false; //打印每一次执行结果
}
