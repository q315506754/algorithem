package com.jiangli.jni.common;

import com.jiangli.common.utils.PathUtil;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/1 0001 13:27
 */
public class Config {
    public  static int test_hWnd=328882;
    public static float smileSimilartity= 0.995f;
    public static String project_path = PathUtil.getProjectPath("JNI").getPath(PathUtil.PATH_SRC_MAIN_RESOURCES);

    //depracated
    public static String findsmile_base_path = PathUtil.buildPath(project_path, "findsmile");
    public static  String capture_path = PathUtil.buildPath(findsmile_base_path,"captured");
    public static  String sample_path = PathUtil.buildPath(findsmile_base_path,"sample");
    public static  String anylyse_path =  PathUtil.buildPath(findsmile_base_path,"anylyse");
//    public static  String characteristic_path =  PathUtil.buildPath(findsmile_base_path,"charac");
    public static  String characteristic_path =  PathUtil.buildPath(findsmile_base_path,"charac_home");


    public static String clickpoints_base_path = PathUtil.buildPath(project_path, "clickpoints");

    //depracated
    public static String continueModule_base_path = PathUtil.buildPath(project_path, "continue");
    public static String continueModule_voice= PathUtil.buildPath(continueModule_base_path, "voice");
//    public static String continueModule_voice_alarm = PathUtil.buildPath(continueModule_voice, "alarm.wav");
    public static String continueModule_voice_alarm = PathUtil.buildPath(continueModule_voice, "main.wav");
    public static String continueModule_path_charac = PathUtil.buildPath(continueModule_base_path, "charac");

}
