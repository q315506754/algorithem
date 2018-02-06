package com.jiangli.common.lib;

import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Jiangli
 * @date 2018/2/6 9:56
 */
public class Describer {
    private static final String[] filteredPropertys = new String[]{"serialVersionUID"};

    public static void putObj(Map<String, Object> map, String key, Object val, Class valCls, String... filteredKey) {
        try {
            //initial
            if (valCls == null && val!=null) {
                valCls = val.getClass();
            } else if (valCls == null && val==null) {
                map.put(key, val);
                return;
            }

            //put
            if(ClassUtils.isPrimitiveOrWrapper(valCls) || valCls == String.class){
                map.put(key, val);
            }
            else if (Iterable.class.isAssignableFrom(valCls)) {
                List list = new ArrayList();

                if (val != null) {
                    Iterable iter = (Iterable) val;
                    iter.forEach(o -> list.add(o));
                }
                map.put(key, list);
            }
            else if (Map.class.isAssignableFrom(valCls)) {
                Map<String,Object> parsedMap = new HashMap<>();

                if (val != null) {
                    Map valMap = (Map) val;
                    valMap.keySet().forEach(o -> putObj(parsedMap,String.valueOf(o),valMap.get(o),null,filteredKey));
                }
                map.put(key, parsedMap);
            }
            else {
                map.put(key, describeObject(val,valCls,filteredKey));
            }
        } finally {
            //filter
            filterMap(map, filteredKey);
        }

    }

    public static List<Map<String, Object>> describeObject(Collection collection, String... filteredKey) {
        int size = collection == null?0:collection.size();
        List<Map<String, Object>> ret = new ArrayList<>(size);
        if (collection != null) {
            for (Object o : collection) {
                ret.add(describeObject(o,filteredKey));
            }
        }
        return ret;
    }

    public static Map<String, Object> describeObject(Object content, Class contentCls,String... filteredKey) {
        Map<String, Object> describe = new HashMap<>();
        if (contentCls == null) {
            return describe;
        }

        if (content instanceof Map) {
            if (content != null) {
                Map valMap = (Map) content;
                valMap.keySet().forEach(o -> putObj(describe,String.valueOf(o),valMap.get(o),null,filteredKey));
            }
            return describe;
        }

        Method[] methods = contentCls.getDeclaredMethods();
        for (Method method : methods) {
            try {
                String methodName = method.getName();
                if (methodName.startsWith("get")) {
                    String fieldName = methodName.substring(3, 4).toLowerCase() + methodName.substring(4);
                    Class<?> returnType = method.getReturnType();

                    Object invoke = null;
                    if (content != null) {
                        invoke = method.invoke(content);
                    }
                    putObj(describe,fieldName,invoke,returnType,filteredKey);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //filter
        filterMap(describe, filteredKey);
        return describe;

    }

    public static Map<String, Object> describeObject(Object content, String... filteredKey) {
        Map<String, Object> describe = new HashMap<>();
        if (content == null) {
            return describe;
        }

        Class cls = content.getClass();
        return describeObject(content,cls,filteredKey);
    }

    private static void filterMap(Map<String, Object> describe, String[] filteredKey) {
        for (String filteredProperty : filteredPropertys) {
            describe.remove(filteredProperty);
        }
        for (String filteredProperty : filteredKey) {
            describe.remove(filteredProperty);
        }
    }
}
