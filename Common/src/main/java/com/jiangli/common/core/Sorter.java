package com.jiangli.common.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:30
 */
public abstract class Sorter<T> implements Sort<T> {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());


    protected <T> T[] getClonedArray(T[] arr) {
        if (arr.length == 0) {
            return null;
        }
        T[] dest = (T[]) Array.newInstance(arr[0].getClass(), arr.length);
        System.arraycopy(arr, 0, dest, 0, arr.length);
        return dest;
    }
}
