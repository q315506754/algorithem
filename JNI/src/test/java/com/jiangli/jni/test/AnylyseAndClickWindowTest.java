package com.jiangli.jni.test;

import com.jiangli.jni.common.*;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/1 0001 13:30
 */
public class AnylyseAndClickWindowTest {
    private double similarity = 0.91d;
    private double point_offset = 30;
    private static  String anylysePath =  Config.anylyse_path;
    private Color MATCH_COLOR = new Color(0,0,0);
    private Color CLICK_POINT_COLOR = new Color(0,0,0);
    private int CLICK_POINT_LENGTH = 5;

    @Test
    public final void testDrawRedLine() {
        List<BMP> characs = getBMPList(Config.characteristic_path);
        for (BMP bmp : characs) {
             System.out.println(bmp.getWidth()+"x"+bmp.getHeight()+" "+bmp.getData().length);
        }

        List<BMP> captures = getBMPList(Config.capture_path);

        int startCount = 0;
        int rectCount = 0;
        for (BMP bmp : captures) {
            List<Rect> rects = new LinkedList<>();
            byte[] captureData = bmp.getData();
            System.out.println(bmp.getWidth()+"x"+bmp.getHeight()+" "+ captureData.length);

            for (BMP characOne : characs) {
                Color startColor = characOne.getColorObj(0, 0);

                for(int cx=0;cx<bmp.getWidth();cx++){
                    for(int cy=0;cy<bmp.getHeight();cy++) {
                        Color bmpColor = bmp.getColorObj(cx, cy);
//                        System.out.println("start cmp:"+bmpColor + " " + startColor);
                        if (bmpColor .equals(startColor)) {
                            startCount ++;
                            if (compare(cx,cy,bmp,characOne)) {
                                rects.add(new Rect(cx, cy, characOne.getWidth(), characOne.getHeight()));
                                rectCount++;

                            }


                        }
                    }
                }


            }


            //draw
            for (Rect rect : rects) {
                for (int i = rect.getX(); i < rect.getX() + rect.getWidth(); i++) {
                    bmp.setColorObj(i,rect.getY(),MATCH_COLOR);
                    bmp.setColorObj(i, rect.getY() + rect.getLength(), MATCH_COLOR);
                }
                for (int j = rect.getY(); j < rect.getY() + rect.getLength(); j++) {
                    bmp.setColorObj(rect.getX(),j,MATCH_COLOR);
                    bmp.setColorObj(rect.getX() + rect.getWidth(), j, MATCH_COLOR);
                }
            }


            //get point
            ClickPoints clickPoints = new ClickPoints();
            for (Rect rect : rects) {
                clickPoints.add(new Point((2*rect.getX()+rect.getWidth())/2,(2*rect.getY()+rect.getLength())/2));
            }

            //draw point
            for (Point point : clickPoints.pointList) {
//                bmp.setColorObj(point.getX(), point.getY(), CLICK_POINT_COLOR);
                for (int i = point.getX() - CLICK_POINT_LENGTH; i < point.getX() + CLICK_POINT_LENGTH; i++) {
                    bmp.setColorObj(i,point.getY(),CLICK_POINT_COLOR);
                }
                for (int i = point.getY() - CLICK_POINT_LENGTH; i < point.getY() + CLICK_POINT_LENGTH; i++) {
                    bmp.setColorObj(point.getX(),i,CLICK_POINT_COLOR);
                }

            }
            System.out.println(clickPoints.pointList.size());

            //write
            try {
                BufferedImage repainted = ImageIO.read(new ByteArrayInputStream(bmp.getData()));
                File dir = new File(anylysePath);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                File outFile = new File(anylysePath+"\\"+bmp.getFile().getName());
                outFile.createNewFile();
                ImageIO.write(repainted, "bmp", new FileOutputStream(outFile));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


//        System.out.println("rects:"+rects.size());
//        System.out.println("rects:"+rects);
        System.out.println(startCount);
        System.out.println("rectCount:"+rectCount);


    }

    class ClickPoints{
        public  List<Point> pointList = new ArrayList<>();

        public void add(Point point){
            for (Point existPo : pointList) {
                if (withinOffset(point.getX()-existPo.getX()) && withinOffset(point.getY()-existPo.getY())) {
                    return ;
                }
            }
            pointList.add(point);
        }

        private boolean withinOffset(int x) {
            return Math.abs(x) <= point_offset;
        }
    }
    private boolean compare(int cx, int cy, BMP bmp, BMP charac) {
        int match = 0;
        int count = 0;
        for(int rx=0;rx<charac.getWidth();rx++) {
            for (int ry = 0; ry < charac.getHeight(); ry++,count++) {
                Color iterColor = charac.getColorObj(rx, ry);
                Color bmpColor = bmp.getColorObj(rx + cx, ry + cy);
                if (bmpColor.equals(iterColor)) {
                    match++;
//                    return false;
                }

//                if (dismatch>200) {
//                    return false;
//                }

            }
        }
        return (match*1.0/count) >= similarity;
    }

    private List<BMP> getBMPList(String pathname) {
        File characFiles = new File(pathname);
        List<BMP> ret = new LinkedList<>();
        if (characFiles.isDirectory()) {
            File[] files = characFiles.listFiles();
            for (File file1 : files) {
                if (file1.isFile()) {
                    BMP bmp = new BMP(file1);
                    ret.add(bmp);
                }
            }
        }
        return ret;
    }

}
