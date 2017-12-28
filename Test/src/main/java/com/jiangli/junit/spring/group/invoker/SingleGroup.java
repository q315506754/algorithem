package com.jiangli.junit.spring.group;

import com.jiangli.junit.spring.group.invoker.GroupInvoker;
import org.junit.runners.model.FrameworkMethod;

/**
 * @author Jiangli
 * @date 2017/12/28 9:32
 */
public class SingleGroup extends CommonGroup {
    public SingleGroup(FrameworkMethod fTestMethod, Object fTarget) {
        super(fTestMethod, fTarget);
    }

    @Override
    public GroupInvoker[] splitGroup(String[] params) {
        return new GroupInvoker[]{new GroupInvoker(fTestMethod, fTarget, null, "默认分组")};
    }
}
