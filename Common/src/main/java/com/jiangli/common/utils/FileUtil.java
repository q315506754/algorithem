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

    public static void openPicture(File file) {
        try {
            Runtime.getRuntime().exec("mspaint \"" + file.getAbsolutePath() + "\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void openPicture(String path) {
        openPicture(new File(path));
    }

    public static void openDirectory(File file) {
        try {
            Runtime.getRuntime().exec("explorer.exe \"" + file.getAbsolutePath() + "\"");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openDirectory(String path) {
        openDirectory(new File(path));
    }

    public static int deleteFilesUnderDir(String dirPath) {
        File dir = new File(dirPath);
        int count = 0;
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    file.delete();
                    count++;
                }
            }
        }
        return count;

    }

}
