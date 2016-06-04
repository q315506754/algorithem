package com.jiangli.jni.app.impl;

import com.jiangli.common.utils.FileUtil;
import com.jiangli.graphics.impl.JavaCVImgThreadMatcher;
import com.jiangli.graphics.impl.MetaIMG;
import com.jiangli.graphics.inf.SimilarStrategy;
import com.jiangli.jni.common.Config;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/3 0003 10:43
 */
public class FindSmileJavaCVThreadMathcer extends JavaCVImgThreadMatcher {

    public FindSmileJavaCVThreadMathcer() {
        super(new SimilarStrategy() {
            @Override
            public float getSimilar(Object charac) {
                File file = new  File(charac.toString());
//                System.out.println(charac);
                if (file.getName().contains("smile")) {
                    System.out.println("smile pic:"+file.getName());
                    return 0.997f;
                }
                return -1;
            }
        });
    }

    @Override
    public List<MetaIMG> loadCharacs() {
        List<String> charPaths = FileUtil.getFilePathFromDirPath(Config.characteristic_path);
        List<MetaIMG> ret = new LinkedList<>();
        for (String charPath : charPaths) {
            MetaIMG one = new MetaIMG(cvLoadImage(charPath),charPath);
            ret.add(one);
        }
        return ret;
    }
}
