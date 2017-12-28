package com.jiangli.junit.spring.group.invoker;

import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;

/**
 * @author Jiangli
 * @date 2017/12/28 9:40
 */
public class GroupInvoker extends Statement {
    private final FrameworkMethod fTestMethod;
    private final Object fTarget;
    private final Object[] params;
    private final String groupName;

    public GroupInvoker(FrameworkMethod fTestMethod, Object fTarget, Object[] params, String groupName) {
        this.fTestMethod = fTestMethod;
        this.fTarget = fTarget;
        this.params = params;
        this.groupName = groupName;
    }

    @Override
    public void evaluate() throws Throwable {
        if (params != null && params.length > 0) {
            fTestMethod.invokeExplosively(fTarget, params);
        } else {
            fTestMethod.invokeExplosively(fTarget);
        }
    }

    public FrameworkMethod getfTestMethod() {
        return fTestMethod;
    }

    public Object getfTarget() {
        return fTarget;
    }

    public Object[] getParams() {
        return params;
    }

    public String getGroupName() {
        return groupName;
    }
}
