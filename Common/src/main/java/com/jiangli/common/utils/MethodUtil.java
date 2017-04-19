package com.jiangli.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/7 0007 10:13
 */
public class MethodUtil {
    public static boolean isGetter(Method method) {
        if (method.getDeclaringClass() != Object.class
                && method.getReturnType() != void.class
                && method.getParameterTypes().length == 0
                && Modifier.isPublic(method.getModifiers())
                && ! Modifier.isStatic(method.getModifiers())) {
            return true;
        }
        return false;
    }
    public static boolean isSetter(Method method) {
        if (method.getDeclaringClass() != Object.class
                && method.getReturnType() == void.class
                && method.getParameterTypes().length == 1
                && Modifier.isPublic(method.getModifiers())
                && ! Modifier.isStatic(method.getModifiers())) {
            return true;
        }
        return false;
    }

    public static Class<?> getBoxedClass(Class<?> c) {
        if( c == int.class )
            c = Integer.class;
        else if( c == boolean.class )
            c = Boolean.class;
        else  if( c == long.class )
            c = Long.class;
        else if( c == float.class )
            c = Float.class;
        else if( c == double.class )
            c = Double.class;
        else if( c == char.class )
            c = Character.class;
        else if( c == byte.class )
            c = Byte.class;
        else if( c == short.class )
            c = Short.class;
        return c;
    }

    public static String getGetterString(String property) {
        return "get" + property.substring(0, 1).toUpperCase() + property.substring(1);
    }
    public static String getIsGetterString(String property) {
        return "is" + property.substring(0, 1).toUpperCase() + property.substring(1);
    }
    public static String getSetterString(String property) {
        return "set" + property.substring(0, 1).toUpperCase() + property.substring(1);
    }

    public static Method getGetter(Class cls,String property) throws NoSuchMethodException {
        String getterString = getGetterString(property);

        Method method = null;
        try {
            method = cls.getMethod(getterString, new Class[]{});
        } catch (Exception e) {
//            e.printStackTrace();
            getterString = getIsGetterString(property);

            method = cls.getMethod(getterString, new Class[]{});
        }

        if (!MethodUtil.isGetter(method)) {
            throw new NoSuchMethodError("is Not Getter:"+getterString);
        }
        return method;
    }

    public static Class<?> getGetterReturnType(Class cls, String property) throws NoSuchMethodException {
        Method getter = getGetter(cls, property);
        Class<?> returnType = getter.getReturnType();
        return returnType;
    }
    public static Method getSetter(Class cls,String property) throws NoSuchMethodException {
        Method getter = getGetter(cls, property);
        Class<?> returnType = getter.getReturnType();

        String setterString = getSetterString(property);
        Method method = cls.getMethod(setterString, new Class[]{returnType});
        if (!MethodUtil.isSetter(method)) {
            throw new NoSuchMethodError("is Not Setter:"+setterString);
        }
        return method;
    }

    public static Object invokeGetter(Object obj,String property) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> aClass = obj.getClass();
        Method getter = getGetter(aClass, property);
        Object invoke = getter.invoke(obj);
        return invoke;
    }
    public static Object invokeSetter(Object obj,String property,Object value) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> aClass = obj.getClass();
        Method setter = getSetter(aClass, property);
        Object invoke = setter.invoke(obj,value);
        return invoke;
    }
    public static void copyProp(Object from,String property,Object to){
        try {
            invokeSetter(to,property,invokeGetter(from,property));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
