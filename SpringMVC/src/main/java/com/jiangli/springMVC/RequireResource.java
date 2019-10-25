package com.jiangli.springMVC;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jiangli
 * @date 2019/8/28 16:04
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequireResource {
    String[] value();

    boolean isMainPage() default false;
}
