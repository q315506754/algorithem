package com.jiangli.springMVC.utils.iterutil;

import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jiangli
 * @date 2020/6/6 11:32
 */
public class MethoParamUtil {
    public static List<MethoParam> parse(Method method) {
        List<MethoParam> ret = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        for (Parameter parameter : parameters) {
            RequestParam annotation = parameter.getAnnotation(RequestParam.class);
            if (annotation != null) {
                MethoParam one = new MethoParam();
                one.reqQame = annotation.value();
                one.isMust = annotation.required();
                one.defaultVal = annotation.defaultValue();
                one.type = parameter.getType().getSimpleName();
                one.name = parameter.getName();
                ret.add(one);
            }
        }
        return ret;
    }
}
