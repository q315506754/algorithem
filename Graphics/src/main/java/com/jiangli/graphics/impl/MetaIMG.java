package com.jiangli.graphics.impl;

import org.bytedeco.javacpp.opencv_core;

public class MetaIMG{
    public opencv_core.IplImage image;
    public  Object src;

    public MetaIMG() {
    }

    public MetaIMG(opencv_core.IplImage image, Object src) {
        this.image = image;
        this.src = src;
    }

}