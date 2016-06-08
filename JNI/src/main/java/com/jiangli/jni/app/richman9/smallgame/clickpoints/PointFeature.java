package com.jiangli.jni.app.richman9.smallgame.clickpoints;

import com.jiangli.graphics.common.Color;
import com.jiangli.graphics.common.Point;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/8 0008 11:37
 */
public class PointFeature {
    private Point point;
    private Color color;
    private Color colorOffset;

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

    public Color getColorOffset() {
        return colorOffset;
    }

    public void setColorOffset(Color colorOffset) {
        this.colorOffset = colorOffset;
    }
}
