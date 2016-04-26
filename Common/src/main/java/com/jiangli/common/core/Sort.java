package com.jiangli.common.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:30
 */
public interface Sort<T> {
    T[] sort(T[] arr);
}
