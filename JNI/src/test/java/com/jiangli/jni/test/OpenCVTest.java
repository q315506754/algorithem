package com.jiangli.jni.test;

import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
import org.junit.Test;
/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/2 0002 10:42
 */
public class OpenCVTest {
    @Test
    public final void testInstance() throws Exception {
//        IplImage image=cvLoadImage("");
//        if(image!=null){
//            cvSmooth(image,image,CV_GAUSSIAN,3);
//            cvSaveImage("lina.jpg",image);
//            cvReleaseImage(image);
//        }
        FrameGrabber grabber = FrameGrabber.createDefault(0);
        grabber.start();

        CanvasFrame frame = new CanvasFrame("Some Title", CanvasFrame.getDefaultGamma()/grabber.getGamma());

        int x =1000000;

    }

}
