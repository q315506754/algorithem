package com.jiangli.junit.spring.group;

import org.junit.runners.model.FrameworkMethod;

/**
 * @author Jiangli
 * @date 2017/12/28 9:31
 */
public abstract class CommonGroup implements GroupInf{
    protected final FrameworkMethod fTestMethod;
    protected final Object fTarget;

    public CommonGroup(FrameworkMethod fTestMethod, Object fTarget) {
        this.fTestMethod = fTestMethod;
        this.fTarget = fTarget;
    }
}
