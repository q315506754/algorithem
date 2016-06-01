package com.jiangli.jni.test;

import com.jiangli.jni.common.*;
import com.jiangli.jni.core.User32;
import com.jiangli.jni.core.struct.Rect;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class WinTest {
    private static User32 user32 = User32.INSTANCE;
    private static  String short_path = "C:\\Users\\Administrator\\Desktop\\tN";
    private static  int  test_hnd = 460564;
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public final void testInstance() {
        System.out.println(user32);
    }

    @Test
    public final void testLoad() {
        System.out.println(Config.characteristic_path);
    }

    @Test
    public final void testDll() {
        // 定义一个结构体变量
        Rect rect = new Rect();
        // 取得桌面句柄
        int hwnd = User32.INSTANCE.GetDesktopWindow();
        // 取整个屏幕大小
        User32.INSTANCE.GetWindowRect(hwnd, rect);
        System.out.println("宽 = " + rect.width + " . 高 = " + rect.height);
        System.out.println(rect);
    }

    @Test
    /**
     * FindWindowA是ANSI页windows里的函数，FindWindow是一样的。
     HWND WINAPI FindWindow(
     __in_opt  LPCTSTR lpClassName,
     __in_opt  LPCTSTR lpWindowName
     );
     */
    public final void testFindWindowA() {
        String title="fdfdf";
        System.out.println(user32.FindWindowA(null, "sdsd"));
        System.out.println(user32.FindWindowA("ffs", "sdsd"));//??,title
        System.out.println(user32.FindWindowA(null, ""));
        System.out.println(user32.FindWindowA("", ""));
        System.out.println(user32.FindWindowA(null, "FolderView"));
        System.out.println(user32.FindWindowA(null, "米柚"));
        int qq = user32.FindWindowA(null, "qq");
        System.out.println(qq);
        System.out.println(user32.FindWindowA(null, "taobaoprotect"));
        System.out.println("MIUI 1:"+user32.FindWindowA("CHWindow", "米柚"));
//        int i = user32.FindWindowA("CHWindow", null);
//        int i = user32.GetDesktopWindow();
        int i = user32.FindWindowA(null, "大富翁9");
//        int i = 199612;
        System.out.println("大富翁9 :"+ i);
        try {
            File file = HwndUtil.shortCut(i, short_path);
            System.out.println(file);
            System.out.println("\""+file.getPath());
            System.out.println("\""+file.getAbsolutePath());

            Runtime.getRuntime().exec("mspaint \"" + file.getAbsolutePath()+"\"");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("MIUI:"+user32.FindWindowA("米柚手游模拟器", null));

        int hwnd = Window.getHwnd("QQ游戏 - 连连看角色版");
        System.out.println(hwnd);
    }

    private com.jiangli.jni.common.Color CLICK_POINT_COLOR = new com.jiangli.jni.common.Color(0,0,0);
    private int CLICK_POINT_LENGTH = 5;


    @Test
    public final void testCaptureAndCick() throws Exception {
        int hWnd = test_hnd;
        Point point = new Point(100, 68);

        BMP bmp = null;
        File file = HwndUtil.shortCut(hWnd, Config.capture_path);
        bmp = new BMP(file);

        for (int i = point.getX() - CLICK_POINT_LENGTH; i < point.getX() + CLICK_POINT_LENGTH; i++) {
            bmp.setColorObj(i,point.getY(),CLICK_POINT_COLOR);
        }
        for (int i = point.getY() - CLICK_POINT_LENGTH; i < point.getY() + CLICK_POINT_LENGTH; i++) {
            bmp.setColorObj(point.getX(),i,CLICK_POINT_COLOR);
        }

        //write
        try {
            BufferedImage repainted = ImageIO.read(new ByteArrayInputStream(bmp.getData()));
            File dir = new File(Config.anylyse_path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String name = null;
            if (bmp.getFile() != null) {
                name = bmp.getFile().getName();
            } else {
                name = HwndUtil.generateName(hWnd)+".bmp";
            }
            File outFile = new File(Config.anylyse_path+"\\"+ name);
            outFile.createNewFile();
            FileOutputStream output = new FileOutputStream(outFile);
            ImageIO.write(repainted, "bmp", output);
            output.flush();
            output.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        int n = 10;
        while (n-- > 0) {
            Mouse.click(hWnd, point.getX(), point.getY());
        }


    }

    @Test
    public final void testMouse_click_desk() {

        Mouse.click(132512, 100, 100);
    }

    @Test
    public final void testMouse_click() {
        int n= 10;
        while(n-->0) {
//            System.out.println(user32.PostMessageA(100, 200, 300, 400));
            Mouse.click(test_hnd, 200, 200);
            System.out.println("test3");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    //
	/*
	 * ShowWindow() Commands
	 */
//	#define SW_HIDE             0
//	#define SW_SHOWNORMAL       1
//	#define SW_NORMAL           1
//	#define SW_SHOWMINIMIZED    2
//	#define SW_SHOWMAXIMIZED    3
//	#define SW_MAXIMIZE         3
//	#define SW_SHOWNOACTIVATE   4
//	#define SW_SHOW             5
//	#define SW_MINIMIZE         6
//	#define SW_SHOWMINNOACTIVE  7
//	#define SW_SHOWNA           8
//	#define SW_RESTORE          9
//	#define SW_SHOWDEFAULT      10
//	#define SW_FORCEMINIMIZE    11
//	#define SW_MAX              11
    @Test
    public  void testShow() {
        // 定义一个结构体变量
        Rect rect = new Rect();
        // 取得桌面句柄
        int hwnd = User32.INSTANCE.GetDesktopWindow();

//        System.out.println(User32.INSTANCE.ShowWindow(132512, 6));
        System.out.println(User32.INSTANCE.ShowWindow(132512, 3));
    }
}
