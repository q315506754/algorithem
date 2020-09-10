package com.jiangli.springMVC.utils.iterutil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * 获得所有rest 接口链接
 *
 * @author Jiangli
 * @date 2018/8/31 15:10
 */
public class RestControllerUrlUtil {
    public static void main(String[] args) {
        //String basePackage = "com.zhishi.org.api.saasweb";
        String basePackage = "com.jiangli.springMVC.controller";
        String domain = "http://orgapi.g2s.cn";
        String yu_domain = "http://120.92.138.210:20010";
        String local_domain = "http://localhost";

        //List<String> list = Arrays.asList("com.zhihuishu.qa.controller.teacher");
        PkgClsUtil.processBasePkg(basePackage,(pkgName, aClass) -> {
            //if (list.contains(pkgName)) {
            //    System.out.println(pkgName+" "+aClass);

                String clPath = null;

            //AnnotationUtils.findAnnotation()

                if ((aClass.isAnnotationPresent(Controller.class) || aClass.isAnnotationPresent(RestController.class))
                        && aClass.isAnnotationPresent(RequestMapping.class) ) {

                    RequestMapping declaredAnnotation = (RequestMapping) aClass.getAnnotation(RequestMapping.class);
                    clPath = getRelaPath(declaredAnnotation);

                    Method[] methods = aClass.getMethods();
                    for (Method method : methods) {
                        String mePath = null;
                        String type = "POST";

                        if (method.isAnnotationPresent(RequestMapping.class)) {
                             mePath = getRelaPath(method.getAnnotation(RequestMapping.class));
                        }
                        if (method.isAnnotationPresent(GetMapping.class)) {
                             mePath = method.getAnnotation(GetMapping.class).value()[0];
                             type = "GET";
                        }
                        if (method.isAnnotationPresent(PostMapping.class)) {
                             mePath = method.getAnnotation(PostMapping.class).value()[0];
                        }

                        if (mePath != null) {
                            System.out.println(" "+aClass.getSimpleName() + " " + method.getName());

                            mePath=mePath+"?uid=dgr8OO6y&companyId=47";
                            String buildUrl = PkgClsUtil.buildUrl(PkgClsUtil.removeEndSlash(domain), clPath, mePath);
                            String yu_buildUrl = PkgClsUtil.buildUrl(PkgClsUtil.removeEndSlash(yu_domain), clPath, mePath);
                            String local_buildUrl = PkgClsUtil.buildUrl(PkgClsUtil.removeEndSlash(local_domain), clPath, mePath);
                            System.out.println("外网 "+buildUrl);
                            System.out.println("预发 "+yu_buildUrl);
                            System.out.println("本地 "+local_buildUrl);

                            ShowDocDto showDocDto = new ShowDocDto();
                            showDocDto.type = type;
                            showDocDto.url = buildUrl;
                            showDocDto.yu_url = yu_buildUrl;
                            showDocDto.local_url = local_buildUrl;
                            showDocDto.methods = MethoParamUtil.parse(method);
                            ShowDocUtil.createShowDocAndPrint(showDocDto);

                            System.out.println("--------------------------------");
                            System.out.println("--------------------------------");
                        }
                    }
                }
            //}
        });
    }

    private static String getRelaPath(RequestMapping declaredAnnotation) {
        return declaredAnnotation.value()[0];
    }

}
