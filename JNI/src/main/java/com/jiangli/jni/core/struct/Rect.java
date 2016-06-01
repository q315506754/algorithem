package com.jiangli.jni.core.struct;

import com.sun.jna.Structure;

//矩形结构体
public class Rect extends Structure{

    public int left;
    public int top;
    public int right;
    public int bottom;
    public int width;
    public int height;
    @Override
    public String toString() {
        return "Rect [left=" + left + ", top=" + top + ", right=" + right + ", bottom=" + bottom + ", width=" + width + ", height=" + height + "]";
    }



}
