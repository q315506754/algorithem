package com.jiangli.swing.formatter;

import com.jiangli.swing.ObjStringFormatter;

import java.math.BigDecimal;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/7 0007 10:45
 */
public class DoubleStringFormatter implements ObjStringFormatter<Double> {
    private int scale;

    public DoubleStringFormatter(int scale) {
        this.scale = scale;
    }

    public int getScale() {
        return scale;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    @Override
    public Double getValueFromString(String str) {
        return new BigDecimal(str).setScale(scale,BigDecimal.ROUND_DOWN).doubleValue();
    }

    @Override
    public String parseIntoString(Double value) {
        return new BigDecimal(value).setScale(scale,BigDecimal.ROUND_DOWN).toString();
    }
}
