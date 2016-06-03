package com.jiangli.jni.test;

import com.jiangli.common.utils.PathUtil;
import com.jiangli.graphics.common.BMP;
import com.jiangli.graphics.common.Color;
import com.jiangli.graphics.common.Rect;
import com.jiangli.jni.common.Config;
import com.jiangli.jni.common.DrawUtil;
import com.jiangli.graphics.common.Point;
import org.junit.Test;

import java.io.File;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/2 0002 17:19
 */
public class DrawUtilTest {

    @Test
    public void func() {
        BMP bmp = new BMP(PathUtil.buildPath(Config.capture_path,"2016-06-02 19.56.41.871 hnd-66964.bmp"));
        Point point = new Point(615, 463);
//        Point point = new Point(1243, 110);
        File file = DrawUtil.drawPointCross(bmp, point, 50, new Color(0, 0, 0));
        DrawUtil.openPicture(file);
    }

    @Test
    public void func2() {
        BMP bmp = new BMP(PathUtil.buildPath(Config.capture_path,"2016-06-03 14.40.34.841 hnd-67236.bmp"));
//        Point point = new Point(615, 463);
//        Point point = new Point(1243, 110);
        File file = DrawUtil.drawRect(bmp, new Rect(580,210,400,400), new Color(255, 0, 0));
        DrawUtil.openPicture(file);
    }
}
