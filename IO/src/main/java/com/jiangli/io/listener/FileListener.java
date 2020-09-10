package com.jiangli.io.listener;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import java.io.File;

public class FileListener extends FileAlterationListenerAdaptor {


    // 文件创建执行
    @Override
    public void onFileCreate(File file) {
        System.out.println("onFileCreate"+" "+file);
        System.out.println(file.isAbsolute());
        System.out.println(file.canRead());
        System.out.println(file.length());
    }

    // 文件创建修改
    @Override
    public void onFileChange(File file) {
        // 触发业务
        //listenerService.doSomething();
        System.out.println("onFileChange"+" "+file);
        System.out.println(file.length());
    }

    // 文件创建删除
    @Override
    public void onFileDelete(File file) {
        System.out.println("onFileDelete"+" "+file);
    }

    // 目录创建
    @Override
    public void onDirectoryCreate(File directory) {
        System.out.println("onDirectoryCreate"+" "+directory);
    }

    // 目录修改
    @Override
    public void onDirectoryChange(File directory) {
        System.out.println("onDirectoryChange"+" "+directory);
    }

    // 目录删除
    @Override
    public void onDirectoryDelete(File directory) {
        System.out.println("onDirectoryDelete"+" "+directory);
    }


    @Override
    public void onStart(FileAlterationObserver observer) {
        //System.out.println("onStart"+" "+observer);

    }

    // 轮询结束
    @Override
    public void onStop(FileAlterationObserver observer) {
        //System.out.println("onStop"+" "+observer);
    }
}