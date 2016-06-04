package com.jiangli.graphics.impl;

import com.jiangli.graphics.common.BMP;
import com.jiangli.graphics.common.Point;
import com.jiangli.graphics.inf.SimilarStrategy;
import com.jiangli.graphics.match.GraphicMatcher;
import org.bytedeco.javacpp.opencv_core;

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
    private SimilarStrategy similarStrategy;


    public JavaCVImgThreadMatcher() {
        this(null);
    }

    public JavaCVImgThreadMatcher(SimilarStrategy similarStrategy) {
        super();
        this.similarStrategy = similarStrategy;
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
        logger.debug("matched points:" + matchedPoints);
        return matchedPoints;
    }


    private void startThreads() {
        for (MetaIMG charac_image : charac_images) {
            Thread thread = null;
            if (similarStrategy != null) {
                float similarity=0f;
                similarity = similarStrategy.getSimilar(charac_image.src);
                thread = new Thread(new JavaCVImgInnerMatcherThread(charac_image.image,similarity));
            } else {
                thread = new Thread(new JavaCVImgInnerMatcherThread(charac_image.image));
            }


            thread.start();
            threads.add(thread);
        }

    }

    class JavaCVImgInnerMatcherThread implements Runnable {
        private opencv_core.IplImage characImage;
        private Float fixedSimilar;

        public JavaCVImgInnerMatcherThread(opencv_core.IplImage characImage) {
            this.characImage = characImage;
        }

        public JavaCVImgInnerMatcherThread(opencv_core.IplImage characImage, Float similar) {
            this.characImage = characImage;
            this.fixedSimilar = similar;
        }

        @Override
        public void run() {
            try {
                while (true) {

                    synchronized (lock) {
                        logger.debug(Thread.currentThread() + " waiting lock...");
                        lock.wait();
                    }


                    Point match = null;

                    if (this.fixedSimilar != null && this.fixedSimilar > 0) {
                        logger.debug("used fixedSimilar:"+this.fixedSimilar);
                        //use local
                        match = GraphicMatcher.match(iplImage, characImage,this.fixedSimilar);
                    } else {
                        logger.debug("used global:"+similar);
                        //use global
                        match = GraphicMatcher.match(iplImage, characImage, similar);
                    }


                    if (match != null) {
                        logger.debug(Thread.currentThread() + " find Point " + match);
                        matchedPoints.add(getClickablePoint(match, characImage));
                    }

//                    Thread.sleep(3000);

                    logger.debug(Thread.currentThread() + " countDown execute...");
                    latch.countDown();

                    logger.debug(Thread.currentThread() + " loop over...");


                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
