package com.jiangli.jni.app.llk;

import com.jiangli.graphics.common.BMP;
import com.jiangli.jni.common.Mouse;
import com.jiangli.graphics.common.Point;
import com.jiangli.jni.common.Window;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Lianlk {



    public static int getHwnd(String windTitle) {
        int hwnd = Window.getHwnd("QQ游戏 - 连连看角色版");
        // hwnd = 1511386;
        if (hwnd <= 0) {
            throw new NullPointerException("找不到窗口");
        }
        return hwnd;
    }

    public static File shortCut(int hWnd) throws FileNotFoundException, IOException {
        File f = File.createTempFile("qqllk", ".bmp");
        System.out.println(f.getAbsolutePath());

        // 写出位图
        BufferedImage buffImage = Window.getImage(hWnd);
        if (buffImage == null) {
            throw new NullPointerException("写位图时出错");
        }
        ImageIO.write(buffImage, "bmp", new FileOutputStream(f));
        return f;
    }


    // 秒杀
    public static void ms() throws FileNotFoundException, IOException {
        // 取游戏窗口句柄
        int hwnd = getHwnd("QQ游戏 - 连连看角色版");
//         int hwnd = 199438; //手动

        // 窗口截图
        File shortCut = shortCut(hwnd);

        try {
            // 解析位图
            BMP bmp = new BMP(shortCut.getAbsolutePath());

            // QQ连连看是 11 * 19 矩阵
            int[][] n = new int[11][19];
            FkSet set = new FkSet();
            for (int i = 0; i < 11; i++) {
                for (int j = 0; j < 19; j++) {
                    // 截取一小块 15 *15 的数据
                    int x = 17 + j * 31;
                    int y = 187 + i * 35;
                    Fangkuai fk = new Fangkuai(bmp.getData(x, y, 15, 15));
                    if (bmp.getColor(x, y) != 7359536) {// 这个值是空白区的颜色值
                        int type = set.add(fk);
                        if (type != 0) {
                            n[i][j] = type;
                        }
                    }

                }
            }
            System.out.println(set.size());



            //根据方块集合数据计算并点击
            DepthFirst df = new DepthFirst(n);
            while (df.search()) {
                Point a = df.getA();
                Point b = df.getB();
                Mouse.click(hwnd, a.getY() * 31 + 17, a.getX() * 35 + 187);
                Mouse.click(hwnd, b.getY() * 31 + 17, b.getX() * 35 + 187);
                df.setValue(a.getX(), a.getY(), 0);
                df.setValue(b.getX(), b.getY(), 0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Lianlk.ms();
    }
}
