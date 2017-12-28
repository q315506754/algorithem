package com.jiangli.junit.spring.group;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jiangli
 * @date 2017/12/28 9:24
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface InvokerGroup {
    AvailableGroup value() default AvailableGroup.DEFAULT;

    String[] params() default {};
}
