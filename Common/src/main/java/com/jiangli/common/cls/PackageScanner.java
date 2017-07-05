package com.jiangli.common.cls;

import java.io.File;
import java.util.*;

/**
 * @author Jiangli
 * @date 2017/7/5 8:53
 */
public class PackageScanner {
    public static Map<ClassInfo,List<ClassInfo>> combineInfAndImpl(List<ClassInfo> infList, List<ClassInfo> implList){
        Map<ClassInfo, List<ClassInfo>> ret = new LinkedHashMap<ClassInfo, List<ClassInfo>>();
        for (ClassInfo classInfo : infList) {
            ret.put(classInfo,new ArrayList<ClassInfo>());
        }

        for (ClassInfo implClassInfo : implList) {
            Class implCls = implClassInfo.getCls();
            for (ClassInfo inf : ret.keySet()) {
                Class infCls = inf.getCls();
                if (infCls.isAssignableFrom(implCls)) {
                    ret.get(inf).add(implClassInfo);
                }
            }
        }
        return  ret;
    }

    public static List<ClassInfo> scan(String baseDirPackage, PackageFilter packageFilter, ClassFilter classFilter){
        List<ClassInfo> ret = new LinkedList<ClassInfo>();

        try {
//            URL resource = RemoteResult.class.getClassLoader().getResource(baseDirPackage);
//            File dir = new File(resource.toURI());

            String base = new File("").getAbsolutePath() ;
            File dir = new File(base ,"src/main/java/"+baseDirPackage) ;

            return scan(baseDirPackage,"/",dir,packageFilter,classFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }


    public static String needSlash(String str){
        if (!str.endsWith("/")) {
            str+="/";
        }
        return str;
    }
    public static String eliminateSlash(String str){
        if (str.endsWith("/")) {
            str=str.substring(0,str.length()-1);
        }
        return str;
    }

    public static List<ClassInfo> scan(String baseDirPackage, String relativePackage, File dir, PackageFilter packageFilter, ClassFilter classFilter){
        List<ClassInfo> ret = new LinkedList<ClassInfo>();

        try {
            if (dir.exists()&& dir.isDirectory()) {
                File[] files = dir.listFiles();
                for (File child : files) {
                    String currentPackage = baseDirPackage +eliminateSlash(relativePackage) ;
                    String fileName = child.getName();

                    if (child.isDirectory()) {
                        String relative = needSlash(relativePackage) + fileName;

                        ret.addAll(scan(baseDirPackage,relative,child,packageFilter,classFilter));
                    }else {
                        if (packageFilter!=null) {
                            if(!packageFilter.filter(baseDirPackage,relativePackage,currentPackage,fileName))
                                continue;
                        }

                        if (fileName.endsWith(".java")) {
                            ClassInfo one = new ClassInfo();
                            one.setBasePath(baseDirPackage);
                            one.setRelativePath(relativePackage);

                            String clsName = (needSlash(currentPackage) + fileName.replaceAll(".java", "")).replaceAll("/","\\.");
                            one.setClsName(clsName);

                            Class<?> aClass = Class.forName(clsName);
                            one.setCls(aClass);

                            if (classFilter==null || classFilter.filter(one)) {
                                ret.add(one);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;
    }
}
