package com.jiangli.swing;

import com.jiangli.common.core.ValueDecorator;

import java.awt.*;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/7 0007 11:19
 */
public class ScreenPercentageBindingY implements ValueDecorator<Double, Integer> {
    @Override
    public Integer decorate(Double srcVal) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return (int) (srcVal * screenSize.getHeight() / 100);
    }

}
