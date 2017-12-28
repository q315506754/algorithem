package com.jiangli.junit.spring.group;

import com.jiangli.junit.spring.group.invoker.FixedLengthStringGroup;
import com.jiangli.junit.spring.group.invoker.SingleGroup;

/**
 * @author Jiangli
 * @date 2017/12/28 9:26
 */
public enum AvailableGroup {
    FIXED_LENGTH_STRING(FixedLengthStringGroup.class),
    DEFAULT(SingleGroup.class);

    private Class<? extends CommonGroup> groupClass;

    AvailableGroup(Class<? extends CommonGroup> groupClass) {
        this.groupClass = groupClass;
    }

    public Class<? extends CommonGroup> getGroupClass() {
        return groupClass;
    }
}
