package com.jiangli.graphics.impl;

import com.jiangli.graphics.common.BMP;
import com.jiangli.graphics.common.Point;
import com.jiangli.graphics.inf.BMPMatcher;
import com.jiangli.graphics.match.GraphicMatcher;
import org.bytedeco.javacpp.opencv_core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/3 0003 10:35
 */
public abstract class JavaCVImgThreadMatcher extends JavaCVImgMatcher {
    private CountDownLatch latch;
    private Object lock = new Object();
    protected List<Thread> threads = new LinkedList<>();
    private List<Point> matchedPoints;
    private opencv_core.IplImage iplImage;
    private float similar;

    public JavaCVImgThreadMatcher() {
        super();
        startThreads();
    }

    @Override
    public List<Point> match(BMP bigImg, float similarity) {
        //reset
        iplImage = cvLoadImage(bigImg.getFile().getPath());
        matchedPoints = new LinkedList<>();
        similar = similarity;
        latch = new CountDownLatch(threads.size());

        synchronized (lock) {
            lock.notifyAll();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.debug("matched points:"+matchedPoints);
        return matchedPoints;
    }


    private void startThreads() {
        for (opencv_core.IplImage charac_image : charac_images) {
            Thread thread = new Thread(new JavaCVImgInnerMatcherThread(charac_image));
            thread.start();
            threads.add(thread);
        }

    }

    class JavaCVImgInnerMatcherThread implements Runnable {
        private opencv_core.IplImage characImage;

        public JavaCVImgInnerMatcherThread(opencv_core.IplImage characImage) {
            this.characImage = characImage;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    synchronized (lock) {
                        logger.debug(Thread.currentThread() + " waiting lock...");
                        lock.wait();
                    }


                    Point match = GraphicMatcher.match(iplImage, characImage, similar);

                    if (match != null) {
                        logger.debug(Thread.currentThread() + " find Point "+match);
                        matchedPoints.add(getClickablePoint(match, characImage));
                    }

//                    Thread.sleep(3000);

                    logger.debug(Thread.currentThread() + " countDown execute...");
                    latch.countDown();

                    logger.debug(Thread.currentThread() + " loop over...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
