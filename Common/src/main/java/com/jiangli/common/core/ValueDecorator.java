package com.jiangli.common.core;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/7 0007 11:09
 */
public interface ValueDecorator<S,D> {
    D decorate(S srcVal);
}
