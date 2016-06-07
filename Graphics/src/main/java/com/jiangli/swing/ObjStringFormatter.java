package com.jiangli.swing;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/7 0007 10:40
 */
public interface ObjStringFormatter<T> {
    T getValueFromString(String str);

   String parseIntoString(T value);


}
