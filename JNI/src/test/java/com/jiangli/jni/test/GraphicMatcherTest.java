package com.jiangli.jni.test;

import com.jiangli.common.utils.FileUtil;
import com.jiangli.common.utils.PathUtil;
import com.jiangli.common.utils.TimeAnalyser;
import com.jiangli.graphics.common.BMP;
import com.jiangli.graphics.common.Color;
import com.jiangli.graphics.common.Point;
import com.jiangli.graphics.impl.RmoveDuplicatePointFilter;
import com.jiangli.graphics.inf.BMPMatcher;
import com.jiangli.graphics.inf.PointFilter;
import com.jiangli.graphics.javacv.TemplateMatch;
import com.jiangli.graphics.match.GraphicMatcher;
import com.jiangli.jni.app.richman9.smallgame.findsmile.FindSmileJavaCVThreadMathcer;
import com.jiangli.jni.common.Config;
import com.jiangli.jni.common.DrawUtil;
import com.jiangli.jni.common.HwndUtil;
import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacpp.opencv_core;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/2 0002 17:52
 */
public class GraphicMatcherTest {

    private BMPMatcher mathcer ;

    @Test
    public void testBMPMatcher_multi() {
        mathcer = new FindSmileJavaCVThreadMathcer();

        String capturePath = PathUtil.buildPath(PathUtil.getProjectPath("JNI").getProjectBasePath(), PathUtil.PATH_SRC_MAIN_RESOURCES, "findsmile", "captured");
        List<String> capturePaths = FileUtil.getFilePathFromDirPath(capturePath);
        System.out.println(capturePaths);

        for (String capturePathOne : capturePaths) {
            anylyseOneImg(capturePathOne);
        }
    }

    @Test
    public void testCVBytes() throws Exception {
        Loader.load();
        BMP bmp = HwndUtil.captureAndGetBMP(Config.test_hWnd, null);
        opencv_core.IplImage iplImage = cvLoadImage(new BytePointer(bmp.getData()));
        System.out.println(iplImage);

    }
    @Test
    public void testCVBytes2() throws Exception {
        File file = HwndUtil.shortCut(Config.test_hWnd);
        opencv_core.IplImage iplImage = cvLoadImage(file.getAbsolutePath());
        System.out.println(iplImage);

    }
    @Test
    public void testBMPMatcher() {
        mathcer = new FindSmileJavaCVThreadMathcer();

        String bigFfile = PathUtil.buildPath(PathUtil.getProjectPath("JNI").getProjectBasePath(), PathUtil.PATH_SRC_MAIN_RESOURCES, "findsmile", "captured", "2016-06-02 19.56.41.871 hnd-66964.bmp");

        anylyseOneImg(bigFfile);
    }

    public void anylyseOneImg(String bigFfile) {
        TimeAnalyser analyser = new TimeAnalyser();

//        BMPMatcher mathcer = new FindSmileJavaCVMathcer();
//        mathcer = new FindSmileJavaCVThreadMathcer();
//        analyser.push("[x]read characs");

        BMP bigImg = new BMP(bigFfile);
        List<Point> points = mathcer.match(bigImg, Config.smileSimilartity);
        analyser.push("match point");
        analyser.setTitle(bigFfile);

        PointFilter pointFilter = new RmoveDuplicatePointFilter(20);
        pointFilter.filter(points);

        for (Point point : points) {
            DrawUtil.drawPointCross(bigImg, point, 50, new Color(255, 0, 0));
        }
        analyser.push("[x]draw point");

        File file = null;
//        file =  DrawUtil.writeFile(bigImg);

        //write
        try {
            file = new File(Config.anylyse_path + "\\" + bigImg.getFile().getName());
            DrawUtil.writeFile(bigImg, file);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        analyser.push("[x]write file");

//        DrawUtil.openPicture(file);

        analyser.analyse();
    }

    @Test
    public void testOne() {
        System.out.println("START...");
        String filename = PathUtil.buildPath(PathUtil.getProjectPath("JNI").getProjectBasePath(), PathUtil.PATH_SRC_MAIN_RESOURCES, "findsmile", "charac", "laugh_loud_0.bmp");
        System.out.println(filename);

        String bigFfile = PathUtil.buildPath(PathUtil.getProjectPath("JNI").getProjectBasePath(), PathUtil.PATH_SRC_MAIN_RESOURCES, "findsmile", "captured", "2016-06-02 19.56.41.871 hnd-66964.bmp");
//        String bigFfile = PathUtil.buildPath(PathUtil.getProjectPath("JNI").getProjectBasePath(), PathUtil.PATH_SRC_MAIN_RESOURCES, "findsmile", "captured", "2016-06-01 17.32.19.963 hnd-460564.bmp");
        System.out.println(bigFfile);

        List<Point> points = GraphicMatcher.matchMulti(bigFfile, filename);
        System.out.println(points);

        Point match = GraphicMatcher.match(bigFfile, filename);
//        Point match = GraphicMatcher.match(filename,bigFfile);
        System.out.println(match);

        TemplateMatch tm = new TemplateMatch();//实例化TemplateMatch对象
        tm.load(filename);
        boolean b = tm.matchTemplate(cvLoadImage(bigFfile));
        System.out.println(b);
    }

    @Test
    public void test1() {
        String charPath = Config.characteristic_path;
        String capturePath = Config.capture_path;

        List<String> charPaths = FileUtil.getFilePathFromDirPath(charPath);
        System.out.println(charPaths);

        List<String> capturePaths = FileUtil.getFilePathFromDirPath(capturePath);
        String[] strings = capturePaths.toArray(new String[capturePaths.size()]);
        System.out.println(capturePaths);

        for (String capturePathOne : capturePaths) {
            long l = System.currentTimeMillis();
            List<Point> points = GraphicMatcher.matchMulti(capturePathOne, strings);
            long cost = System.currentTimeMillis()-l;
            System.out.println("stage1 "+cost+" ms ");
            BMP bmp = new BMP(capturePathOne);
            for (Point point : points) {
                DrawUtil.drawPointCross(bmp, point, 50, new Color(255, 0, 0));
                System.out.println(point);
            }
            System.out.println("points:"+points.size());

            File outFile = new File(Config.anylyse_path + "\\" + bmp.getFile().getName());
            DrawUtil.writeFile(bmp,outFile);
             cost = System.currentTimeMillis()-l;
            System.out.println("total:"+cost+" ms "+outFile);
        }
    }
}
