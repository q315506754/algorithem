package com.jiangli.springMVC.utils;

import com.jiangli.common.utils.PkgClsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * 获得所有rest 接口链接
 *
 * @author Jiangli
 * @date 2018/8/31 15:10
 */
public class ControllerUrlUtil {
    public static void main(String[] args) {
        String basePackage = "com.jiangli.springMVC";
        String domain = "http://localhost:80/courseqa";

        List<String> list = Arrays.asList("com.jiangli.springMVC", "com.zhihuishu.qa.controller.teacher");
        PkgClsUtil.processBasePkg(basePackage,(pkgName, aClass) -> {
            if (list.contains(pkgName)) {
                System.out.println(pkgName+" "+aClass);

                String clPath = null;
                final String realDomain = PkgClsUtil.removeEndSlash(domain);
                if (aClass.isAnnotationPresent(Controller.class)
                        && aClass.isAnnotationPresent(RequestMapping.class) ) {

                    RequestMapping declaredAnnotation = (RequestMapping) aClass.getAnnotation(RequestMapping.class);
                    clPath = getRelaPath(declaredAnnotation);

                    Method[] methods = aClass.getMethods();
                    for (Method method : methods) {
                        if (method.isAnnotationPresent(RequestMapping.class)) {
                            String mePath = getRelaPath(method.getAnnotation(RequestMapping.class));

                            String buildUrl = PkgClsUtil.buildUrl(realDomain, clPath, mePath);
                            System.out.println(buildUrl);
                        }
                    }
                }
            }
        });
    }

    private static String getRelaPath(RequestMapping declaredAnnotation) {
        return declaredAnnotation.value()[0];
    }

}
