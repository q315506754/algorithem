package com.jiangli.common.utils;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by Jiangli on 2016/6/1.
 */
public class PathUtil {
    public static String PATH_DELIMETER= "\\";
    public static String PATH_SRC= "src";
    public static String PATH_SRC_MAIN= PATH_SRC+PATH_DELIMETER+"main";
    public static String PATH_SRC_MAIN_JAVA= PATH_SRC_MAIN+PATH_DELIMETER+"java";
    public static String PATH_SRC_MAIN_RESOURCES= PATH_SRC_MAIN+PATH_DELIMETER+"resources";

    public static String PATH_TEST= "test";
    public static String PATH_TEST_MAIN= PATH_TEST+PATH_DELIMETER+"main";
    public static String PATH_TEST_MAIN_JAVA= PATH_TEST_MAIN+PATH_DELIMETER+"java";
    public static String PATH_TEST_MAIN_RESOURCES= PATH_TEST_MAIN+PATH_DELIMETER+"resources";

    public static String getClassPath() {
        return getClassPath(PathUtil.class);
    }

    public static String getClassPath(Class cls) {
        try {
            return cls.getClassLoader().getResource("").toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getProjectPath(Class cls) {
        File parent = getTargetPath(getClassPath());

        try {
            return parent.getParentFile().getPath();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBaseProjectPath() {
        File parent = getTargetPath(getClassPath());

        try {
            return parent.getParentFile().getParentFile().getPath();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static File getTargetPath(String classPath) {
        File file = new File(classPath);
        File parent = null;
        while (true) {
            parent = file.getParentFile();
            if (parent.getName().equalsIgnoreCase("target")) {
                break;
            }
        }
        return parent;
    }

    public static String getSRC_JAVA_Path(Class cls) {
        return buildPath(getProjectPath(cls),PATH_SRC_MAIN_JAVA);
    }
    public static String getSRC_JAVA_Code_Path(Class cls) {
        String s = getSRC_JAVA_Path(cls);
        String packagePath = convertClsToFilePath(cls)+".java";
        return buildPath(s,packagePath);
    }

    public static String convertClsToFilePath(Class cls) {
        String name = cls.getName();
//        System.out.println(name);
        name = name.replaceAll("\\.",PATH_DELIMETER+PATH_DELIMETER);
        return name;
    }

    public static String buildPath(String basePath,boolean ensurePath,String... child) {
        String ret = basePath;

        if (child != null) {
            for (String s : child) {
                ret = ret + PATH_DELIMETER + s;
            }
        }

        if (ensurePath) {
            ensurePath(ret);
        }
        return ret;
    }
    public static String buildPath(String basePath,String... child) {
        return buildPath(basePath,true,child);
    }

    public static void ensurePath(String path){
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getProjectPathStr(String project) {
        return  getProjectPath(project).getProjectBasePath();
    }
    public static ProjectPath getProjectPath(String project) {
        return new ProjectPath(project);
    }

    public static class  ProjectPath{
        private String projectBasePath;

        public ProjectPath(String projectName) {
            this.projectBasePath = getBaseProjectPath()+PATH_DELIMETER +projectName;
        }

        public String getProjectBasePath() {
            return projectBasePath;
        }

        public String getPath(String relativePath) {
            String s = projectBasePath + PATH_DELIMETER + relativePath;
            ensurePath(s);
            return s;
        }
    }

}
