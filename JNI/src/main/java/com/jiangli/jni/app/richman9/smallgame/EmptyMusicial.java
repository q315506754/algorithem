package com.jiangli.jni.app.richman9.smallgame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Jiangli on 2016/6/16.
 */
public class EmptyMusicial implements  Musicial {
    private final Component component;

    public EmptyMusicial(Component component) {
        this.component = component;
    }

    @Override
    public void playMusic() {
        JOptionPane.showMessageDialog(component, "没有音乐可以播放", "错误", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void stopMusic() {
        JOptionPane.showMessageDialog(component, "没有音乐可以停止", "错误", JOptionPane.ERROR_MESSAGE);
    }
}
