package com.jiangli.jni.app.richman9.smallgame;

import com.jiangli.graphics.common.BMP;

import java.io.IOException;

/**
 * Created by Jiangli on 2016/6/15.
 */
public interface Capturable {
    int MODE_DEFAULT=0;
    int MODE_PARTIAL=1;
    int MODE_FULL=2;

    BMP captureBMP() throws IOException;
    BMP captureBMP(int mode) throws IOException;

    String getCaptureFilePath() ;
}
