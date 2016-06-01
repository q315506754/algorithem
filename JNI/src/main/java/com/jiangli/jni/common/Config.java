package com.jiangli.jni.common;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/1 0001 13:27
 */
public class Config {
    public static  String capture_path = "C:\\Users\\Administrator\\Desktop\\tN";
    public static  String anylyse_path =  Config.capture_path + "\\anylyse";
    public static  String characteristic_path =  Config.capture_path + "\\charac";

    static {
        try {
//            String path = Config.class.getClassLoader().getResource("findsmile").toURI().getPath();
            characteristic_path =    "C:\\AlgorithemProject\\JNI\\src\\main\\resources\\findsmile\\charac";

        } catch (Exception e) {
            e.printStackTrace();

            characteristic_path =  Config.capture_path + "\\charac";
        }


    }

}
