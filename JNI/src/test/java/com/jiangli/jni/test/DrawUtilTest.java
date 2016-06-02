package com.jiangli.jni.test;

import com.jiangli.jni.common.BMP;
import com.jiangli.jni.common.Color;
import com.jiangli.jni.common.DrawUtil;
import com.jiangli.graphics.common.Point;
import org.junit.Test;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/2 0002 17:19
 */
public class DrawUtilTest {

    @Test
    public void func() {
        BMP bmp = new BMP("C:\\AlgorithemProject\\JNI\\src\\main\\resources\\findsmile\\captured\\2016-06-01 14.10.34.252 hnd-460564.bmp");
        Point point = new Point(621, 244);
//        Point point = new Point(1243, 110);
        DrawUtil.drawPointCross(bmp, point, 5, new Color(255,0,0));
    }
}
