package com.jiangli.jni.app.richman9.smallgame.findsmile;

import com.jiangli.common.utils.TimeAnalyser;
import com.jiangli.graphics.common.BMP;
import com.jiangli.graphics.common.Color;
import com.jiangli.graphics.common.Point;
import com.jiangli.graphics.impl.RmoveDuplicatePointFilter;
import com.jiangli.graphics.inf.BMPMatcher;
import com.jiangli.graphics.inf.PointFilter;
import com.jiangli.jni.app.richman9.smallgame.AbsractAnalyser;
import com.jiangli.jni.common.Config;
import com.jiangli.jni.common.DrawUtil;

import java.io.File;
import java.util.List;

/**
 * Created by Jiangli on 2016/6/4.
 */
public class FindSmileDirAnalyser extends AbsractAnalyser {
    private BMPMatcher mathcer = new FindSmileJavaCVThreadMathcer();
    public FindSmileDirAnalyser() {
        super(Config.findsmile_base_path);
    }

    @Override
    protected void anylyseEachPath(String bigFfile) {
        TimeAnalyser analyser = new TimeAnalyser();

//        BMPMatcher mathcer = new FindSmileJavaCVMathcer();
//        mathcer = new FindSmileJavaCVThreadMathcer();
//        analyser.push("[x]read characs");

        BMP bigImg = new BMP(bigFfile);
        List<Point> points = mathcer.match(bigImg, Config.smileSimilartity);
        analyser.push("match point");
        analyser.setTitle(bigFfile);

        PointFilter pointFilter = new RmoveDuplicatePointFilter(20);
        pointFilter.filter(points);

        for (Point point : points) {
            DrawUtil.drawPointCross(bigImg, point, 50, new Color(255, 0, 0));
        }
        analyser.push("[x]draw point");

        File file = null;
//        file =  DrawUtil.writeFile(bigImg);

        //write
        try {
            file = new File(analysePath+ "\\" + bigImg.getFile().getName());
            DrawUtil.writeFile(bigImg, file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        analyser.push("[x]write file");

//        DrawUtil.openPicture(file);

        analyser.analyse();
    }

}
