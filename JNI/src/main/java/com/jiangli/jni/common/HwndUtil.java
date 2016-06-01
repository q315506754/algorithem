package com.jiangli.jni.common;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/1 0001 9:59
 */
public class HwndUtil {
    public static File shortCut(int hWnd) throws FileNotFoundException, IOException {
        File f = File.createTempFile(generateName(hWnd), ".bmp");

        captureAndWriteToFile(hWnd, f);
        return f;
    }

    public static File shortCut(int hWnd,String path) throws FileNotFoundException, IOException {

        File f_path = new File(path);

        if (!f_path.exists()) {
            f_path.mkdirs();
        }

        File f = new File(path + "/" + generateName(hWnd) + ".bmp");
        captureAndWriteToFile(hWnd, f);

        return f;
    }

    private static void captureAndWriteToFile(int hWnd, File f) throws IOException {
        // 写出位图
        BufferedImage buffImage = Window.getImage(hWnd);
        if (buffImage == null) {
            throw new NullPointerException("写位图时出错");
        }
        FileOutputStream output = new FileOutputStream(f);
        ImageIO.write(buffImage, "bmp", output);
        output.close();
    }

    public static BMP captureAndGetBMP(int hWnd) throws IOException {
        // 写出位图
        BufferedImage buffImage = Window.getImage(hWnd);
        if (buffImage == null) {
            throw new NullPointerException("写位图时出错");
        }
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(buffImage, "bmp", output);
        output.flush();
        output.close();
        BMP ret = new BMP();
        ret.setData(output.toByteArray());
        ret.setWidth(buffImage.getWidth());
        ret.setHeight(buffImage.getHeight());
        return ret;
    }

    public static String generateName(int hWnd) {
        return new SimpleDateFormat("yyyy-MM-dd HH.mm.ss.SSS").format(new Date())+" hnd-" + hWnd;
    }

}
