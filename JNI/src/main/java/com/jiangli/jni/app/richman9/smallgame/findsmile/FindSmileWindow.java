package com.jiangli.jni.app.richman9.smallgame.findsmile;

import com.jiangli.common.utils.SwingUtil;
import com.jiangli.graphics.common.RectPercentage;
import com.jiangli.graphics.inf.BMPMatcher;
import com.jiangli.jni.app.richman9.smallgame.Analyser;
import com.jiangli.jni.app.richman9.smallgame.AnylyseAndClickWindow;
import com.jiangli.jni.app.richman9.smallgame.continueModule.ClickContinueBtnPointsListener;
import com.jiangli.jni.app.richman9.smallgame.continueModule.MainMusicPiano;
import com.jiangli.jni.common.Config;

import java.util.Optional;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/7 0007 17:23
 */
public class FindSmileWindow extends AnylyseAndClickWindow {
    @Override
    public Analyser getDirAnalyser() {
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
        return Config.findsmile_base_path;
    }

    @Override
    protected void childrenInitialStart() {
        offsetPercentage = new RectPercentage(27.00,18.50,18.00,32.00);
        btnFire.setText("点击笑脸");
        setMousePressDuration(1,30);
        setMousePressinterval(200,600);
        pointsListeners.add(new ClickContinueBtnPointsListener(this));
        try {
            piano = Optional.of(new MainMusicPiano());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void childrenInitialEnd() {
        this.setTitle("找笑脸");
        this.setSize(400, 500);
        SwingUtil.setFrameRelativePos(this, 90, 50);
    }

    public static void main(String[] args) {
        FindSmileWindow window = new FindSmileWindow();
    }
}
