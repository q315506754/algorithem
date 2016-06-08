package com.jiangli.jni.app.richman9.smallgame;

import com.jiangli.graphics.common.Color;
import com.jiangli.graphics.common.Point;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/8 0008 16:35
 */
public class PointColor {
    private Point point;
    private Color color;
    private int type=-1;

    @Override
    public String toString() {
        return "PointColor{" +
                "point=" + point +
                ", color=" + color +
                ", type=" + type +
                '}';
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public PointColor(Point point, Color color) {
        this.point = point;
        this.color = color;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
