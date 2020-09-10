package com.jiangli.springMVC.utils.iterutil;

import java.io.File;
import java.net.URL;
import java.util.function.BiConsumer;

/**
 * @author Jiangli
 * @date 2018/8/31 15:10
 */
public class PkgClsUtil {
    public static String prependSlash(String str) {
        if (str!=null) {
            str = str.trim();
            if (!str.startsWith("/")) {
                return "/"+str;
            }
        }
        return str;
    }
    public static String removeEndSlash(String str) {
        if (str!=null) {
            str = str.trim();
            if (str.endsWith("/")) {
                return str.substring(0,str.length()-1);
            }
        }
        return str;
    }
    public static String buildUrl(String... strs) {
        StringBuilder sb =new StringBuilder();

        int n = 0;
        for (String str : strs) {
            if (n > 0) {
                sb.append(prependSlash(removeEndSlash(str)));
            } else if (n == strs.length-1){
                sb.append(prependSlash(str));
            } else {
                sb.append(removeEndSlash(str));
            }

            n++;
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        String basePackage = "com.jiangli.common.utils";
        String domain = "http://localhost:80/courseqa";

        processBasePkg(basePackage, (pkgName, aClass) -> {
            System.out.println(pkgName + " " + aClass);
        });
    }


    public static void processBasePkg(String basePackage, BiConsumer<String,Class> consumer) {
        String path = (basePackage).replaceAll("\\.","/");
        //System.out.println(path);
        //System.out.println(ClassLoader.getSystemResource(""));

        //URL resource = ClassLoader.getSystemResource(path);
        URL resource = PkgClsUtil.class.getResource(prependSlash(path));
        //System.out.println(resource);
        String filePath = resource.getFile();
        File file = new File(filePath);

        if (file.isDirectory()) {
            processDir(file,basePackage,consumer);
        }
    }

    public static void processDir(File file, String basePackage, BiConsumer<String,Class> consumer) {
        if (file!=null && file.isDirectory()) {
            File[] files = file.listFiles();
            for (File cd : files) {
                String name = cd.getName();
                if (name.endsWith(".class")) {
                    String clsName = basePackage + "."+name.substring(0,name.lastIndexOf(".class"));
                    try {
                        Class<?> aClass = Class.forName(clsName);
                        consumer.accept(basePackage,aClass);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (cd.isDirectory()) {
                    processDir(cd,basePackage+"."+name,consumer);
                }
            }
        }
    }
}
