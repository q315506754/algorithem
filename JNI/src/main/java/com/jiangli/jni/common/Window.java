package com.jiangli.jni.common;


import com.jiangli.jni.core.User32;
import com.jiangli.jni.core.struct.Rect;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Window {

    private static User32 user32 = User32.INSTANCE;

    // 取窗口句柄
    public static int getHwnd(String title) {
        return user32.FindWindowA(null, title);
//        return user32.FindWindowW(null, title);
    }

    // 取窗口矩形
    public static Rect getRect(int hwnd) {
        Rect r = new Rect();
        user32.GetWindowRect(hwnd, r);
        return r;
    }

    //窗口截图
    public static BufferedImage getImage(int hwnd){
        return getImage(hwnd,null);
    }

    //窗口截图
    public static BufferedImage getImage(int hwnd,com.jiangli.graphics.common.Rect rect){
        Rect r = Window.getRect(hwnd);
        Rectangle rg = null;
        if (rect != null) {
             rg = new Rectangle(r.left+rect.getX(), r.top+rect.getY(), rect.getLength(), rect.getWidth());
        } else {
             rg = new Rectangle(r.left, r.top, r.right-r.left, r.bottom-r.top);
        }
        try {
            return new Robot().createScreenCapture(rg);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
