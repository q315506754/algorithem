package com.jiangli.jni.app.richman9.smallgame.clickpoints;

import com.jiangli.common.utils.SwingUtil;
import com.jiangli.graphics.common.RectPercentage;
import com.jiangli.graphics.inf.BMPMatcher;
import com.jiangli.jni.app.impl.FindSmileJavaCVThreadMathcer;
import com.jiangli.jni.app.richman9.smallgame.AnylyseAndClickWindow;
import com.jiangli.jni.app.richman9.smallgame.findsmile.FindSmileDirAnalyser;
import com.jiangli.jni.common.Config;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/7 0007 17:23
 */
public class ClickPointsWindow extends AnylyseAndClickWindow {
    @Override
    public FindSmileDirAnalyser getDirAnalyser() {
        return new FindSmileDirAnalyser();
    }

    @Override
    public BMPMatcher getBMPMatcher() {
        return new FindSmileJavaCVThreadMathcer();
    }

    @Override
    protected float getSimilarity() {
        return 0.995f;
    }

    @Override
    protected String getProjectBasePath() {
        return Config.clickpoints_base_path;
    }

    @Override
    protected void childrenInitialStart() {
        offsetPercentage = new RectPercentage(25.50,20.50,19.50,34.00);
        btnFire.setText("点点点");
        setMousePressDuration(30,100);
    }

    @Override
    protected void childrenInitialEnd() {
        this.setTitle("点点点");
        this.setSize(400, 500);
        SwingUtil.setFrameRelativePos(this, 90, 50);
    }

    public static void main(String[] args) {
        ClickPointsWindow window = new ClickPointsWindow();
    }
}
