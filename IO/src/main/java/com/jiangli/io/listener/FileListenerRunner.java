package com.jiangli.io.listener;

import org.apache.commons.io.monitor.FileAlterationMonitor;

public class FileListenerRunner  {


    public static void main(String[] args) {
        FileListenerFactory fileListenerFactory = new FileListenerFactory();

        // 创建监听者
        FileAlterationMonitor fileAlterationMonitor = fileListenerFactory.getMonitor();
        try {
            // do not stop this thread
            fileAlterationMonitor.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}