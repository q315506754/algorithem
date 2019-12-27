package com.jiangli.jni.core;

import com.jiangli.jni.core.struct.Rect;
import com.sun.jna.Native;
import com.sun.jna.win32.StdCallLibrary;

public interface User32 extends StdCallLibrary{

    /**
     * echo %path%
     * 其中有路径
     * C:\Windows\System32
     *
     */
    User32 INSTANCE = (User32)Native.loadLibrary("User32",User32.class);

    int PostMessageA(int a,int b,int c,int d);

    int FindWindowA(String a,String b);

    int FindWindowW(String a,String b);

    int GetWindowRect(int hwnd,Rect r);// 取窗口矩形

    int GetDesktopWindow();// 取桌面句柄

    boolean ShowWindow(int hWnd,int nCmdShow);
}
