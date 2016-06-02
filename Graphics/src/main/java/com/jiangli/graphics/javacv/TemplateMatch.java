package com.jiangli.graphics.javacv;


import org.bytedeco.javacpp.opencv_core;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_TM_CCORR_NORMED;
import static org.bytedeco.javacpp.opencv_imgproc.cvMatchTemplate;

public class TemplateMatch {

    private opencv_core.IplImage image;

    public void load(String filename) {
        image = cvLoadImage(filename); 
    }

    public boolean matchTemplate(opencv_core.IplImage source) {
        boolean matchRes;
        opencv_core.IplImage result = cvCreateImage(opencv_core.cvSize(
                source.width() - this.image.width() + 1,
                source.height() - this.image.height() + 1),
                opencv_core.IPL_DEPTH_32F, 1);

        opencv_core.cvZero(result);
        cvMatchTemplate(source, this.image, result, CV_TM_CCORR_NORMED);
//        opencv_core.CvPoint maxLoc = new opencv_core.CvPoint();
//        opencv_core.CvPoint minLoc = new opencv_core.CvPoint();
        int[] maxLoc = new int[2];
        int[] minLoc = new int[2];
        double[] minVal = new double[2];
        double[] maxVal = new double[2];

        cvMinMaxLoc(result, minVal, maxVal, minLoc, maxLoc, null);
        matchRes = maxVal[0] > 0.99f ? true : false;
        System.out.println(maxVal[0]);
        System.out.println(java.util.Arrays.toString(maxLoc));
        System.out.println(java.util.Arrays.toString(minLoc));
        cvReleaseImage(result);
        return matchRes;
    }
}