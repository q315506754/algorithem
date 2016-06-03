package com.jiangli.jni.test;

import com.jiangli.common.utils.FileUtil;
import com.jiangli.common.utils.PathUtil;
import org.junit.Test;

import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/3 0003 13:55
 */
public class ClearPathTest {
    @Test
    public void testRemove() {
        String capturePath = PathUtil.buildPath(PathUtil.getProjectPath("JNI").getProjectBasePath(), PathUtil.PATH_SRC_MAIN_RESOURCES, "findsmile", "captured");
        List<String> capturePaths = FileUtil.getFilePathFromDirPath(capturePath);

    }
}
