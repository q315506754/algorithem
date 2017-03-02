package com.jiangli.log.analyser.core;

import java.io.File;

/**
 * @author Jiangli
 * @date 2017/3/1 15:26
 */
public class CommonLogFileFilter implements  Filter{
    String[] INCLUDE_SUFFIXES = new String[]{"log","out"};

    @Override
    public boolean ignore(File file) {
        String fullName = file.getName();
        int idx = fullName.lastIndexOf(".");
        String suffix = fullName.substring(idx + 1);

//        System.out.println("filter file:"+fullName);
        for (String suf : INCLUDE_SUFFIXES) {
            if (suffix.equalsIgnoreCase(suf) || suffix.startsWith(suf)) {
                return false ;
            }
        }
        return true ;
    }
}
