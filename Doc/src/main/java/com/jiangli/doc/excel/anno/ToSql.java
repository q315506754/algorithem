package com.jiangli.doc.excel.anno;


import com.jiangli.doc.excel.enums.SqlType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/1/17 0017 11:12
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ToSql {
    String columnName();

    SqlType valType();

    boolean dataConvert() default false;
}
