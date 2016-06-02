package com.jiangli.jni.test;

import com.jiangli.common.utils.PathUtil;
import com.jiangli.jni.common.BMP;
import com.jiangli.jni.common.Color;
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
}
