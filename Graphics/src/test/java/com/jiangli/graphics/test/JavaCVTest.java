package com.jiangli.graphics.test;

import com.jiangli.common.utils.PathUtil;
import com.jiangli.graphics.javacv.TemplateMatch;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.*;
import org.bytedeco.javacpp.*;
import org.bytedeco.javacpp.indexer.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_calib3d.*;
import static org.bytedeco.javacpp.opencv_objdetect.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
public class JavaCVTest {

    public static void main(String[] args) {
        System.out.println("START...");
        TemplateMatch tm = new TemplateMatch();//实例化TemplateMatch对象
        String filename = PathUtil.buildPath(PathUtil.getProjectPath("JNI").getProjectBasePath(), PathUtil.PATH_SRC_MAIN_RESOURCES, "findsmile", "charac", "glass_180.bmp");
        System.out.println(filename);
        tm.load(filename);//加载带比对图片，注此图片必须小于源图

        String bigFfile = PathUtil.buildPath(PathUtil.getProjectPath("JNI").getProjectBasePath(), PathUtil.PATH_SRC_MAIN_RESOURCES, "findsmile", "captured", "2016-06-01 14.10.34.252 hnd-460564.bmp");
//        String bigFfile = PathUtil.buildPath(PathUtil.getProjectPath("JNI").getProjectBasePath(), PathUtil.PATH_SRC_MAIN_RESOURCES, "findsmile", "captured", "2016-06-01 17.32.19.963 hnd-460564.bmp");
        System.out.println(bigFfile);
        boolean result = tm.matchTemplate(cvLoadImage(bigFfile));//校验585.png是否包含于原图58home.png
        if (result){//打印匹配结果，boolean
            System.out.println("match");
        }else{
            System.out.println("un-match");
        }
        System.out.println("END...");
    }
}