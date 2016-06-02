package com.jiangli.graphics.match;

import com.jiangli.graphics.common.Point;
import org.bytedeco.javacpp.opencv_core;

import java.util.*;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_TM_CCORR_NORMED;
import static org.bytedeco.javacpp.opencv_imgproc.cvMatchTemplate;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/2 0002 17:37
 */
public class GraphicMatcher {

    public static final float MIN_SIMLILARITY = 0.91f;

    public static Point match(String srcImgPath, String smallImgPath) {
        return match(srcImgPath, smallImgPath, MIN_SIMLILARITY);
    }

    public static List<Point> matchMulti(String srcImgPath, String... smallImgPaths) {
        List<Point> ret = new LinkedList<>();
        opencv_core.IplImage srcImage = cvLoadImage(srcImgPath);
        for (String smallImgPath : smallImgPaths) {
            Point point = match(srcImage, smallImgPath, MIN_SIMLILARITY);
            if (point != null) {
                ret.add(point);
            }
        }
        return ret;

    }

    public static Point match(opencv_core.IplImage srcImage, String smallImgPath, float minSimlilarity) {
        opencv_core.IplImage smallImage = cvLoadImage(smallImgPath);
        opencv_core.IplImage result = cvCreateImage(opencv_core.cvSize(
                        srcImage.width() - smallImage.width() + 1,
                        srcImage.height() - smallImage.height() + 1),
                opencv_core.IPL_DEPTH_32F, 1);

        opencv_core.cvZero(result);
        cvMatchTemplate(srcImage, smallImage, result, CV_TM_CCORR_NORMED);
//        opencv_core.CvPoint maxLoc = new opencv_core.CvPoint();
//        opencv_core.CvPoint minLoc = new opencv_core.CvPoint();
        int[] maxLoc = new int[2];
        int[] minLoc = new int[2];
        double[] minVal = new double[2];
        double[] maxVal = new double[2];

        cvMinMaxLoc(result, minVal, maxVal, minLoc, maxLoc, null);
        Point ret = null;

        if (maxVal[0] > minSimlilarity) {
            ret = new Point(maxLoc[0], maxLoc[1]);

            System.out.println("maxVal:"+java.util.Arrays.toString(maxVal));
            System.out.println("minVal:"+java.util.Arrays.toString(minVal));
//            System.out.println("maxLoc"+java.util.Arrays.toString(maxLoc));
//            System.out.println("minLoc"+java.util.Arrays.toString(minLoc));
        }

        cvReleaseImage(result);

        return ret;
    }

    public static Point match(String srcImgPath, String smallImgPath, float minSimlilarity) {
        opencv_core.IplImage srcImage = cvLoadImage(srcImgPath);
        return match(srcImage, smallImgPath, MIN_SIMLILARITY);
    }
}
