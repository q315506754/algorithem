package com.jiangli.common.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jiangli on 2016/6/6.
 */
public class SwingUtil {
    public static void setCommonFrameStyle(JFrame frame) {
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setDefaultLookAndFeelDecorated(false);
    }
    public static void setFrameRelativePos(Component component, double perX, double perY) {
        Dimension frameSize = component.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }

        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        component.setLocation((int)((screenSize.width - frameSize.width) * perX/100),
                (int)((screenSize.height - frameSize.height) * perY/100));
    }

    public static void setFrameMiddle(Component component) {
        setFrameRelativePos(component,50,50);
    }
    public static void setFrameRight(Component component) {
        setFrameRelativePos(component,100,50);
    }
    public static void setFrameLeft(Component component) {
        setFrameRelativePos(component,0,50);
    }
}
