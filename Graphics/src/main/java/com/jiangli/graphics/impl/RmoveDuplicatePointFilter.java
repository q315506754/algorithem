package com.jiangli.graphics.impl;

import com.jiangli.graphics.common.Point;
import com.jiangli.graphics.inf.PointFilter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/3 0003 15:25
 */
public class RmoveDuplicatePointFilter implements PointFilter {
    private int distance=1;

    public RmoveDuplicatePointFilter(int distance) {
        this.distance = distance;
    }

    @Override
    public void filter(List<Point> points) {
        List<Point> ret = new ArrayList<>(points.size());

        for (Point point : points) {
            boolean exist =  false;

            if (ret.size() >= 0) {
                for (Point esistPoint : ret) {
                    if (withinOffset(point.getX() - esistPoint.getX()) && withinOffset(point.getY() - esistPoint.getY())) {
                        exist = true;
                        break;
                    }
                }
            }

            if (!exist) {
                ret.add(point);
            }
        }

        if (points.size() !=ret.size() ) {
            points.retainAll(ret);
        }

    }


    private boolean withinOffset(int x) {
        return Math.abs(x) <= distance;
    }
}
