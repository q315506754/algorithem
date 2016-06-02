package com.jiangli.jni.test;

import com.jiangli.common.utils.FileUtil;
import com.jiangli.graphics.match.GraphicMatcher;
import com.jiangli.jni.common.Config;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/2 0002 17:52
 */
public class GraphicMatcherTest {

    @Test
    public void test1() {
        String charPath = Config.characteristic_path;
        String capturePath = Config.capture_path;

        List<String> charPaths = FileUtil.getFilePathFromDirPath(charPath);
        System.out.println(charPaths);

        List<String> capturePaths = FileUtil.getFilePathFromDirPath(capturePath);
        capturePaths.toArray(new String[capturePaths.size()]);
        System.out.println(capturePaths);

        for (String capturePathOne : capturePaths) {
//            GraphicMatcher.matchMulti(capturePathOne,)
        }
    }
}
