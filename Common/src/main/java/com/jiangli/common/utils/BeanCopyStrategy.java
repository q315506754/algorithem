package com.jiangli.common.utils;

/**
 * @author Jiangli
 *         <p/>
 *         CreatedTime  2015/3/11 0011 10:59
 */
public interface BeanCopyStrategy<K, V> {
    void copyStrategy(K source, V target);
}
