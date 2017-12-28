package com.jiangli.junit.spring.group;

import com.jiangli.junit.spring.group.invoker.GroupInvoker;

/**
 * @author Jiangli
 * @date 2017/12/28 9:31
 */
public interface GroupInf {
    GroupInvoker[] splitGroup(String[] params);
}
