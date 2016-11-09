package com.jiangli.jdk.tool.aptv1_6;

import java.lang.annotation.*;

/**
 * @author Jiangli
 * @date 2016/11/9 16:57
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface StaticCount {
    int value() default 1;
}
