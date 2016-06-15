package com.jiangli.jni.app.richman9.smallgame.continueModule;

import com.jiangli.common.utils.FileUtil;
import com.jiangli.graphics.common.BMP;
import com.jiangli.graphics.common.Point;
import com.jiangli.graphics.inf.BMPMatcher;
import com.jiangli.jni.app.richman9.smallgame.Capturable;
import com.jiangli.jni.app.richman9.smallgame.CatureAndClickWindow;

import java.util.List;

/**
 * Created by Jiangli on 2016/6/15.
 */
public class ClickContinueBtnPointsListener extends AbstractPointsListener{
    private CatureAndClickWindow catureAndClickWindow;
    private int failedTimes = 0;
    private final int failedThreshold = 25;
//    private final int failedThreshold = 1;
    private final BMPMatcher matcher= new FindContinueBtnJavaCVMathcer();

    public ClickContinueBtnPointsListener(CatureAndClickWindow catureAndClickWindow) {
        this.catureAndClickWindow = catureAndClickWindow;
    }

    @Override
    public void accept(List<Point> points) {
        if (points == null || points.size() == 0) {
            failedTimes ++;
        }else{
            resetFailed();
        }

        if (failedTimes > failedThreshold) {
            resetFailed();

            try {
                logger.debug("start click continue button!");

                 catureAndClickWindow.intialProcess();

                BMP bmp = catureAndClickWindow.captureBMP(Capturable.MODE_FULL);

                List<Point> match = matcher.match(bmp, 0.965f);
                logger.debug("继续按钮:"+match);

                catureAndClickWindow.accept(match);

                //log
                String msg = "已经点击完继续按钮了...";
                catureAndClickWindow.log(msg);
                logger.debug("already clicked continue button");

                //clear capture path
                String captureFilePath = catureAndClickWindow.getCaptureFilePath();
                FileUtil.deleteFilesUnderDir(captureFilePath);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }

    private void resetFailed() {
        failedTimes = 0;
    }
}
