package com.jiangli.jni.common;

import com.jiangli.jni.core.User32;

import java.awt.*;
import java.awt.event.InputEvent;

public class Mouse {

    private static User32 user32 = User32.INSTANCE;

    // 模拟鼠标左键单击
    public static void click(int hwnd, int x, int y) {
        int v = x + (y << 16);
        user32.PostMessageA(hwnd, 513, 1, v);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        user32.PostMessageA(hwnd, 514, 0, v);
    }

    public static void pressByRobot(int hWnd, Robot robot , com.jiangli.graphics.common.Point point) {
//        Mouse.click(hWnd, point.getX(), point.getY());

        java.awt.Point originpoint = null;
        try {
            originpoint = MouseInfo.getPointerInfo().getLocation();
        } catch (Exception e) {
            e.printStackTrace();
        }

        com.jiangli.jni.core.struct.Rect r = Window.getRect(hWnd);

        robot.mouseMove(r.left+ point.getX(),r.top+ point.getY());
        robot.delay(5);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.delay(50);
        robot.mouseRelease(InputEvent.BUTTON1_MASK);

        robot.mouseMove( (int)originpoint.getX(),(int)originpoint.getY());

    }

    // 模拟鼠标左键单击
    public static void press(int hwnd, int x, int y) {
        int v = x + (y << 16);
        user32.PostMessageA(hwnd, 0x0100, 1, v);
        user32.PostMessageA(hwnd, 0x0101, 0, v);
    }

    // 模拟鼠标左键单击
    public static void press(int hwnd, int button) {
        user32.PostMessageA(hwnd, 0x0100, button, 0);
        user32.PostMessageA(hwnd, 0x0101, button, 0);
    }

}
