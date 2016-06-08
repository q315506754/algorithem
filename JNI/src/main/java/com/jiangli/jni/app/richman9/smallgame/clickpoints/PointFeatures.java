package com.jiangli.jni.app.richman9.smallgame.clickpoints;

import com.jiangli.graphics.common.Color;
import com.jiangli.graphics.common.Point;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/8 0008 11:37
 */
public class PointFeatures {
    private List<PointFeature> features = new LinkedList<>();

    public List<PointFeature> getFeatures() {
        return features;
    }

    public void setFeatures(List<PointFeature> features) {
        this.features = features;
    }

    public PointFeatures addFeature(Point point,Color color) {
        PointFeature feature = new PointFeature();
        feature.setPoint(point);
        feature.setColor(color);
        features.add(feature);
        return this;
    }
}
