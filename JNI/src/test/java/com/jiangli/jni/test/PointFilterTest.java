package com.jiangli.jni.test;

import com.jiangli.graphics.common.Point;
import com.jiangli.graphics.impl.RmoveDuplicatePointFilter;
import com.jiangli.graphics.inf.PointFilter;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/3 0003 15:34
 */
public class PointFilterTest {

    @Test
    public void func() {
        List<Point> points = new LinkedList<>();
        points.add(new Point(1, 2));
        points.add(new Point(1, 2));
        points.add(new Point(3, 4));
        points.add(new Point(30, 40));
        PointFilter pointFilter = new RmoveDuplicatePointFilter(20);
        pointFilter.filter(points);
        System.out.println(points);
    }

}
