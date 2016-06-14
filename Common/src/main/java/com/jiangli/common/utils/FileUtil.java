package com.jiangli.common.utils;

import com.jiangli.common.core.FileStringProcesser;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/2 0002 17:55
 */
public class FileUtil {
    public static String SYSTEM_DELIMETER="\r\n";
    public static File process(File src, FileStringProcesser processer) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(src)));
            File outFile = File.createTempFile(getPrefix(src)+System.currentTimeMillis(),getSuffix(src));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
            String line=null;

            while ((line = br.readLine()) != null) {
                String processedLine = processer.process(line);
                bw.write(processedLine+SYSTEM_DELIMETER);
            }
            bw.flush();
            bw.close();
            br.close();
            return outFile;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static File processAndReplace(File src, FileStringProcesser processer) {
        File processed = process(src, processer);
        try {
            FileInputStream fileInputStream = new FileInputStream(processed);
            FileOutputStream fileOutputStream = new FileOutputStream(src);
            IOUtils.copy(fileInputStream, fileOutputStream);
            fileInputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return  src;
    }

    public static String getPrefix(File src) {
        String name = src.getName();
        return name.substring(0, name.lastIndexOf("."));
    }
    public static String getSuffix(File src) {
        String name = src.getName();
        return name.substring(name.lastIndexOf("."));
    }

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
    public static void openFile(File file) {
        try {
            Runtime.getRuntime().exec("notepad \"" + file.getAbsolutePath() + "\"");
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

    public static File generateTemp(String suffix) {
        try {
            return File.createTempFile(System.currentTimeMillis() + "", suffix);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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