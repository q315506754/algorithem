package com.jiangli.graphics.test;

import com.jiangli.common.utils.PathUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Jiangli
 * @date 2017/10/13 9:16
 */
public class ImageTest {

    public static void main(String[] args) throws IOException {
        String filename = PathUtil.buildPath(PathUtil.getProjectPath("JNI").getProjectBasePath(), PathUtil.PATH_SRC_MAIN_RESOURCES, "findsmile", "charac", "laugh_loud_0.bmp");
        System.out.println(filename);

        BufferedImage image = ImageIO.read(new File(filename));
        int width = image.getWidth();
        int height = image.getHeight();
        System.out.println(width+"x"+height);


    }

}
