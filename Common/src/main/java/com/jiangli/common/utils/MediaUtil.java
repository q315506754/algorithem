package com.jiangli.common.utils;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *

 ffmpeg -y -i testcut.mp4 -ss 00:00:42 -to 00:00:45 -c copy testcut_result.mp4
 ffmpeg -y -ss 00:00:41 -i testcut.mp4 -t 4 -c copy testcut_result.mp4
 * @author Jiangli
 */
public class MediaUtil {
    static class ConvertRequests{
        String name;
        List<SplitZone> requests=new ArrayList<>();

        public SplitZone getLast() {
            return requests.get(requests.size() - 1);
        }
        public SplitZone create() {
            requests.add(new SplitZone());
            return getLast();
        }
    }

    static class SplitZone {
        String startTime;
        String endTime;
        String outName;
    }

    public static void splitBatchMedia(File txtFile) {
        String outDirName = FileUtil.getPrefix(txtFile.getName());
        File noDupfile = FileUtil.getNoDupfile(txtFile.getParentFile(), outDirName);
        outDirName = noDupfile.getName();

        splitBatchMedia(txtFile,outDirName);
    }

    public static void splitBatchMedia(File txtFile,String outDirName) {
        FileUtil.processVisit(txtFile,line -> {
            String[] s = line.split(" ");

            ConvertRequests requests = new ConvertRequests();
            int phase = 1;
            for (String cmd : s) {
                cmd = cmd.trim();
                if (StringUtils.isNotBlank(cmd)) {
                    if (phase == 1) {
                        requests.name = cmd;
                        phase++;
                    }else {
                        cmd = cmd.replaceAll("\\.", ":");

                        if (phase == 2) {
                            requests.create().startTime = cmd;
                            phase++;
                        }else if (phase == 3) {
                            requests.getLast().endTime = cmd;
                            phase--;
                        }
                    }
                }
            }

            File dir  = txtFile.getParentFile();
            File inputFile = new File(dir, requests.name);
            if (!inputFile.exists()) {
                throw new IllegalAccessException("can not find file "+inputFile + " from source config "+txtFile);
            }
            for (SplitZone request : requests.requests) {
                splitMedia(inputFile,request.startTime,request.endTime,outDirName);
            }

            return null;
        });
    }

    public static void splitMedia(File inputFile,String timeStart,String timeEnd,String outDirName) {
        try {
            String inputFilePath = inputFile.getAbsolutePath();
            String name = inputFile.getName();
            File outDir = new File(inputFile.getParent(), outDirName);
            if (!outDir.exists()) {
                outDir.mkdirs();
            }
            File noDupOutfile = FileUtil.getNoDupfile(outDir, name);
            Process exec = Runtime.getRuntime().exec("ffmpeg -y -i " + inputFilePath + " -ss " + timeStart + " -to " + timeEnd + " -c copy "+noDupOutfile.getAbsolutePath()+"");
            exec.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        //splitMedia(new File("C:\\工作\\pics\\testcut.mp4"),"00:00:42","00:00:45","testcut");


        //splitBatchMedia(new File("C:\\工作\\pics\\testcut.txt"),"testcut_batch");
        //splitBatchMedia(new File("C:\\工作\\pics\\testcut.txt"));

        FileUtil.acceptDragFile(false,files -> {
            StringBuilder sb= new StringBuilder();

            for (File file : files) {
                splitBatchMedia(file);
                sb.append(file);
                sb.append("\n");
            }
            return sb.toString();
        });

    }
}
