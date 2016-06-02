package com.jiangli.jni.common;

import com.jiangli.graphics.common.Point;
import com.jiangli.graphics.common.Rect;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/2 0002 17:14
 */
public class DrawUtil {
    public static boolean openFile = true;
    public  static File drawPointCross(BMP bmp, Point point, int length, Color color){
        //draw point
        for (int i = point.getX() - length; i < point.getX() + length; i++) {
            bmp.setColorObj(i, point.getY(), color);
        }
        for (int i = point.getY() - length; i < point.getY() + length; i++) {
            bmp.setColorObj(point.getX(), i, color);
        }
        File file = writeFile(bmp);

        openPicture(file);

        return file;
    }

    public  static File drawRect(BMP bmp, Rect rect,  Color color){
        for (int i = rect.getX(); i < rect.getX() + rect.getWidth(); i++) {
            bmp.setColorObj(i, rect.getY(), color);
            bmp.setColorObj(i, rect.getY() + rect.getLength(), color);
        }
        for (int j = rect.getY(); j < rect.getY() + rect.getLength(); j++) {
            bmp.setColorObj(rect.getX(), j, color);
            bmp.setColorObj(rect.getX() + rect.getWidth(), j, color);
        }
        File file = writeFile(bmp);

        openPicture(file);

        return file;
    }
    public static void openPicture(File file) {
        try {
            if (openFile) {
                Runtime.getRuntime().exec("mspaint \"" + file.getAbsolutePath() + "\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static File writeFile(BMP bmp) {
        //write
        try {
            File file = File.createTempFile(System.currentTimeMillis()+"paint", "bmp");

            writeFile(bmp,file);
            return file;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void writeFile(BMP bmp,File file) {
        //write
        try {
            BufferedImage repainted = ImageIO.read(new ByteArrayInputStream(bmp.getData()));
            if (!file.exists()) {
                file.mkdirs();
                file.createNewFile();
            }

            FileOutputStream output = new FileOutputStream(file);
            ImageIO.write(repainted, "bmp", output);
            output.flush();
            output.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
