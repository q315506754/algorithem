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
        try {
            return PathUtil.class.getClassLoader().getResource("").toURI().getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getBaseProjectPath() {
        File file = new File(getClassPath());
        File parent = null;
        while (true) {
            parent = file.getParentFile();
            if (parent.getName().equalsIgnoreCase("target")) {
                break;
            }
        }

        try {
            return parent.getParentFile().getParentFile().getPath();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String buildPath(String basePath,String... child) {
        String ret = basePath;

        if (child != null) {
            for (String s : child) {
                ret = ret + PATH_DELIMETER + s;
            }
        }

        ensurePath(ret);
        return ret;
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
