package com.jiangli.jni.common;

import com.jiangli.jni.core.User32;

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
