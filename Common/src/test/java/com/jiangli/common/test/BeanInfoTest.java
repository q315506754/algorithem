package com.jiangli.common.test;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author Jiangli
 * @date 2016/10/12 15:18
 */
public class BeanInfoTest {
    @Test
    public void func() throws IntrospectionException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(List.class);
            System.out.println(beanInfo);
            MethodDescriptor[] methodDescriptors = beanInfo.getMethodDescriptors();
            for (MethodDescriptor methodDescriptor : methodDescriptors) {
                if (methodDescriptor.getName().equals("addAll")) {
                    System.out.println(BeanUtils.describe(methodDescriptor));
                    ParameterDescriptor[] parameterDescriptors = methodDescriptor.getParameterDescriptors();
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
    }

}
