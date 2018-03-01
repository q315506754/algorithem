package com.jiangli.common.utils;

import com.jiangli.common.core.FileStringProcesser;
import org.apache.commons.io.IOUtils;

import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/2 0002 17:55
 */
public class FileUtil {
    public static String SYSTEM_DELIMETER="\r\n";
    public static void processVisit(File src, FileStringProcesser processer) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(src)));
            String line=null;

            while ((line = br.readLine()) != null) {
                processer.process(line);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //public static void processVisit(File src, FileStringProcesser processer) {
    //
    //}
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
        return getPrefix(src.getName());
    }
    public static String getPrefix(String name) {
        return name.substring(0, name.lastIndexOf("."));
    }
    public static String getSuffix(File src) {
        return getSuffix(src.getName());
    }
    public static String getSuffix(String name) {
        return name.substring(name.lastIndexOf("."));
    }
    public static File getNoDupfile(File src) {
        String name = src.getName();
        if (src.exists()) {
            int i=2;
    
            while (true) {
                String prefix = getPrefix(name)+"_"+i++;
                String suffix = getSuffix(name);
                String path = prefix+suffix;
                File ret = new File(src.getParentFile(), path);
                if (!ret.exists()) {
                    return ret;
                }
            }
        }
        return null;
    }
    
    public static List<String> getFilePathFromDirPath(String dirPath) {
        return getFilePathFromDirPath(dirPath,false);
    }
    public static List<String> getFilePathFromDirPath(String dirPath,boolean includeChildren) {
        List<String> paths = new LinkedList<>();

        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                paths.add(file.getPath());
            }
            else if(file.isDirectory() && includeChildren){
                paths.addAll(getFilePathFromDirPath(file.getPath(),true));
            }
        }
        return paths;
    }
    public static List<File> getFilesFromDirPath(String dirPath, Predicate<File> filePredicate) {
        List<File> paths = new LinkedList<>();

        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                boolean collect  =true;
                if (filePredicate != null) {
                    collect = filePredicate.test(file);
                }
                if(collect)
                    paths.add(file);
            }
            else if(file.isDirectory()){
                paths.addAll(getFilesFromDirPath(file.getPath(),filePredicate));
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
