package com.jiangli.junit.spring.group.invoker;

import com.jiangli.junit.spring.group.CommonGroup;
import org.junit.runners.model.FrameworkMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiangli
 * @date 2017/12/28 9:32
 */
public class FixedLengthStringGroup extends CommonGroup {
    public FixedLengthStringGroup(FrameworkMethod fTestMethod, Object fTarget) {
        super(fTestMethod, fTarget);
    }

    @Override
    public GroupInvoker[] splitGroup(String[] params) {
        if(params == null || params.length == 0){
            return new GroupInvoker[]{new GroupInvoker(fTestMethod, fTarget, new Object[]{null}, "默认分组")};
        }

        List<GroupInvoker> ret=  new ArrayList<GroupInvoker>();
        for (String param : params) {
            int length = Integer.parseInt(param);
            ret.add(new GroupInvoker(fTestMethod, fTarget, new Object[]{build(length)}, "组(字符串长度为"+length+")"));
        }
        return ret.toArray(new GroupInvoker[ret.size()]);
    }

    public String build(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(i % 10);
        }
        return sb.toString();
    }
}
