package com.jiangli.jni.app.richman9.smallgame.continueModule;

import com.jiangli.common.utils.FileUtil;
import com.jiangli.graphics.impl.JavaCVImgThreadMatcher;
import com.jiangli.graphics.impl.MetaIMG;
import com.jiangli.jni.common.Config;

import java.util.LinkedList;
import java.util.List;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/3 0003 10:43
 */
public class FindContinueBtnJavaCVMathcer extends JavaCVImgThreadMatcher {

    @Override
    public List<MetaIMG> loadCharacs() {
        List<String> charPaths = FileUtil.getFilePathFromDirPath(Config.continueModule_path_charac);
        List<MetaIMG> ret = new LinkedList<>();
        for (String charPath : charPaths) {
            MetaIMG one = new MetaIMG(cvLoadImage(charPath),charPath);
            ret.add(one);
        }
        return ret;
    }
}
