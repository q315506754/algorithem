package com.jiangli.jdk.v1_8;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/15 0015 17:17
 */
@Repeatable(MulAnnos.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MulAnno {
    String value();
}
