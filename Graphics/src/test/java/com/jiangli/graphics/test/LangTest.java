package com.jiangli.graphics.test;

import com.jiangli.common.utils.MethodUtil;
import com.jiangli.graphics.common.RectPercentage;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/7 0007 10:05
 */
public class LangTest {
    @Test
    public void func() {
        String property = "left";
        RectPercentage offsetPercentage = new RectPercentage(27.00,18.50,18.00,32.00);
        Class<? extends RectPercentage> aClass = offsetPercentage.getClass();
        String getter = "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
        String setter = "set" + property.substring(0, 1).toUpperCase() + property.substring(1);
        try {
            Method method = aClass.getMethod(getter, new Class[]{});
            if (MethodUtil.isGetter(method)) {
            }
            Object invoke = method.invoke(offsetPercentage, new Object[]{});
            System.out.println(invoke);
            System.out.println(invoke.getClass());
            System.out.println(method.getReturnType());

            Method getter1 = MethodUtil.getGetter(aClass, property);
            System.out.println(getter1);
            Object invoke1 = getter1.invoke(offsetPercentage);
            System.out.println(invoke1);
            Method setter1 = MethodUtil.getSetter(aClass, property);
            System.out.println(setter1);
            Object invoke2 = setter1.invoke(offsetPercentage, 2323d);
            System.out.println(invoke2);

            MethodUtil.invokeSetter(offsetPercentage,"left",1234d);
            Object left = MethodUtil.invokeGetter(offsetPercentage, "left");
            System.out.println(left);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
