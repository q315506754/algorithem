package com.jiangli.jni.app.richman9.smallgame.clickpoints;

import com.jiangli.common.utils.SwingUtil;
import com.jiangli.graphics.common.Color;
import com.jiangli.graphics.common.Point;
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
    private int type_2_2 = 0;
    private int type_3_3 = 1;
    private int type_4_4 = 2;
    private PointFeatures[] features ;

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
//        offsetPercentage = new RectPercentage(25.50,20.50,19.50,34.00); //small pic
        offsetPercentage = new RectPercentage(15.60,18.50,40.50,37.00);  //middle
        btnFire.setText("点点点");
        setMousePressDuration(30,100);


        // features init
        features = new PointFeatures[3];
        for (int i = 0; i < features.length; i++) {
            features[i] = new PointFeatures();
        }
        features[type_2_2].addFeature(new Point(250,220),new Color(86,164,119));
        features[type_2_2].addFeature(new Point(240,220),new Color(85,162,118));
        features[type_2_2].addFeature(new Point(230,220),new Color(84,159,116));

        features[type_3_3].addFeature(new Point(220,220),new Color(82,158,114));
        features[type_3_3].addFeature(new Point(210,220),new Color(80,155,111));


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
