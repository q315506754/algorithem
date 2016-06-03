package com.jiangli.graphics.impl;

import com.jiangli.graphics.common.BMP;
import com.jiangli.graphics.common.Point;
import com.jiangli.graphics.inf.BMPMatcher;
import com.jiangli.graphics.match.GraphicMatcher;
import com.jiangli.graphics.inf.ImgMatcher;
import org.bytedeco.javacpp.opencv_core;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/3 0003 10:35
 */
public abstract class JavaCVImgMatcher implements BMPMatcher {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected List<opencv_core.IplImage> charac_images = new LinkedList<>();

    public JavaCVImgMatcher() {
        List<opencv_core.IplImage> iplImages = loadCharacs();
        for (opencv_core.IplImage iplImage : iplImages) {
            charac_images.add(iplImage);
        }
        logger.debug("characs load over ..."+charac_images.size());
        logger.debug(" detail:"+charac_images);
    }

    @Override
    public List<Point> match(BMP bigImg, float similarity) {
        opencv_core.IplImage iplImage = cvLoadImage(bigImg.getFile().getPath());
        List<Point> matched = match(iplImage, similarity);
        return matched;
    }

    public List<Point> match(opencv_core.IplImage bigImg, float similarity) {
        List<Point> points = new LinkedList<>();
        for (opencv_core.IplImage charac_image : charac_images) {
            Point match = GraphicMatcher.match(bigImg, charac_image, similarity);

            if (match != null) {
                points.add(getClickablePoint(match,charac_image));
            }
        }
        logger.debug("matched points:"+points);

        return points;
    }

    public Point getClickablePoint(Point point,opencv_core.IplImage charac_image) {
        point.setX((2*point.getX() + charac_image.width()) /2);
        point.setY((2*point.getY() + charac_image.height()) /2);
        return point;
    }

    public abstract List<opencv_core.IplImage> loadCharacs();

}
