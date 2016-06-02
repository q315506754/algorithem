package com.jiangli.common.utils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/2 0002 17:55
 */
public class FileUtil {

    public static List<String> getFilePathFromDirPath(String dirPath) {
        List<String> paths = new LinkedList<>();

        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                paths.add(file.getPath());
            }
        }
        return paths;
    }

}
