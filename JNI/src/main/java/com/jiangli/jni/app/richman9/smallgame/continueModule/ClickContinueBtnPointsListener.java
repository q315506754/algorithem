package com.jiangli.jni.app.richman9.smallgame.continueModule;

import com.jiangli.common.utils.FileUtil;
import com.jiangli.graphics.common.BMP;
import com.jiangli.graphics.common.Point;
import com.jiangli.graphics.inf.BMPMatcher;
import com.jiangli.jni.app.richman9.smallgame.Capturable;
import com.jiangli.jni.app.richman9.smallgame.CatureAndClickWindow;
import com.jiangli.jni.app.richman9.smallgame.FailedTimesChecker;

import java.util.List;

/**
 * Created by Jiangli on 2016/6/15.
 */
public class ClickContinueBtnPointsListener extends AbstractPointsListener{
    private CatureAndClickWindow catureAndClickWindow;
    private final BMPMatcher matcher= new FindContinueBtnJavaCVMathcer();
    private FailedTimesChecker checkContinue;
    private FailedTimesChecker stopAndPlayMusic;


    public ClickContinueBtnPointsListener(CatureAndClickWindow catureAndClickWindow) {
        this.catureAndClickWindow = catureAndClickWindow;

        checkContinue = new FailedTimesChecker(35);
        checkContinue.setWhenReached(this::clickContinueBtn);

        stopAndPlayMusic = new FailedTimesChecker(60);
        stopAndPlayMusic.setWhenReached(()->{
            catureAndClickWindow.stopTurboFire();
            catureAndClickWindow.playMusic();
        });
    }

    @Override
    public void accept(List<Point> points) {
        boolean condition = points == null || points.size() == 0;

        checkContinue.incFailedTime(condition);

        stopAndPlayMusic.incFailedTime(condition);

    }

    private boolean clickContinueBtn() {
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
            return match.size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
