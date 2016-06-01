package com.jiangli.jni.common;

import com.jiangli.common.utils.PathUtil;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/1 0001 13:27
 */
public class Config {
    public  String sfsdf= "src";
    public static String project_path = PathUtil.getProjectPath("JNI").getPath(PathUtil.PATH_SRC_MAIN_RESOURCES);
    public static String resource_base_path = PathUtil.buildPath(project_path, "findsmile");
    public static  String capture_path = PathUtil.buildPath(resource_base_path,"captured");
    public static  String anylyse_path =  PathUtil.buildPath(resource_base_path,"anylyse");
    public static  String characteristic_path =  PathUtil.buildPath(resource_base_path,"charac");

}
