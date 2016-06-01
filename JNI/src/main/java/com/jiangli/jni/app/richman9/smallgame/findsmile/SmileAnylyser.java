package com.jiangli.jni.app.richman9.smallgame.findsmile;

import com.jiangli.jni.common.BMP;
import com.jiangli.jni.common.Color;
import com.jiangli.jni.common.Point;
import com.jiangli.jni.common.Rect;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/1 0001 14:21
 */
public class SmileAnylyser {
    private double similarity = 0.91d;
    private double point_offset = 30;

    private List<BMP> characs;
    private String characs_path;

    public SmileAnylyser(String characs_path) {
        this.characs_path = characs_path;
        characs = getBMPList(characs_path);
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


    public ClickPoints  getClickPoints(List<Rect> rects) {
        //get point
        ClickPoints clickPoints = new ClickPoints();
        for (Rect rect : rects) {
            clickPoints.add(new Point((2*rect.getX()+rect.getWidth())/2,(2*rect.getY()+rect.getLength())/2));
        }
        return clickPoints;
    }
    public List<Rect>  searchRect(BMP bmp) {
        List<Rect> rects = new LinkedList<>();
        byte[] captureData = bmp.getData();

        for (BMP characOne : characs) {
            Color startColor = characOne.getColorObj(0, 0);

            for(int cx=0;cx<bmp.getWidth();cx++){
                for(int cy=0;cy<bmp.getHeight();cy++) {
                    Color bmpColor = bmp.getColorObj(cx, cy);
//                        System.out.println("start cmp:"+bmpColor + " " + startColor);
                    if (bmpColor .equals(startColor)) {
                        if (compare(cx,cy,bmp,characOne)) {
                            rects.add(new Rect(cx, cy, characOne.getWidth(), characOne.getHeight()));
                        }

                    }
                }
            }

        }

        return rects;

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
