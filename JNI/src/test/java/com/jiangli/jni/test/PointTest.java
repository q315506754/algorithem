package com.jiangli.jni.test;

import com.jiangli.graphics.common.Point;
import org.junit.Test;

/**
 * Created by Jiangli on 2016/6/8.
 */
public class PointTest {

    @Test
    public void func() throws CloneNotSupportedException {
        Point point =new Point(1,2);
        System.out.println(point);
        Point clone = point.clone();
        System.out.println(clone);
        System.out.println(point == clone);
//        point.cl
    }
}
