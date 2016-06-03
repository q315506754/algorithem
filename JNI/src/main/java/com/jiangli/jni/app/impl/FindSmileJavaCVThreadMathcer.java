package com.jiangli.jni.app.impl;

import com.jiangli.common.utils.FileUtil;
import com.jiangli.graphics.impl.JavaCVImgMatcher;
import com.jiangli.graphics.impl.JavaCVImgThreadMatcher;
import com.jiangli.jni.common.Config;
import org.bytedeco.javacpp.opencv_core;

import java.util.LinkedList;
import java.util.List;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/3 0003 10:43
 */
public class FindSmileJavaCVThreadMathcer extends JavaCVImgThreadMatcher {

    @Override
    public List<opencv_core.IplImage> loadCharacs() {
        List<String> charPaths = FileUtil.getFilePathFromDirPath(Config.characteristic_path);
        List<opencv_core.IplImage> ret = new LinkedList<>();
        for (String charPath : charPaths) {
            ret.add(cvLoadImage(charPath));
        }
        return ret;
    }
}
