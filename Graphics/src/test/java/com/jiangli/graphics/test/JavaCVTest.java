package com.jiangli.graphics.test;

import com.jiangli.common.utils.PathUtil;
import com.jiangli.graphics.javacv.TemplateMatch;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
public class JavaCVTest {

    public static void main(String[] args) {
        System.out.println("START...");
        TemplateMatch tm = new TemplateMatch();//实例化TemplateMatch对象
        String filename = PathUtil.buildPath(PathUtil.getProjectPath("JNI").getProjectBasePath(), PathUtil.PATH_SRC_MAIN_RESOURCES, "findsmile", "charac", "laugh_loud_0.bmp");
        System.out.println(filename);
        tm.load(filename);//加载带比对图片，注此图片必须小于源图

        String bigFfile = PathUtil.buildPath(PathUtil.getProjectPath("JNI").getProjectBasePath(), PathUtil.PATH_SRC_MAIN_RESOURCES, "findsmile", "captured", "2016-06-02 19.56.41.871 hnd-66964.bmp");
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