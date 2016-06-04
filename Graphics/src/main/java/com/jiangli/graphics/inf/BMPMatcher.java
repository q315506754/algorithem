package com.jiangli.graphics.inf;

import com.jiangli.graphics.common.BMP;
import com.jiangli.graphics.common.Point;

import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/3 0003 10:24
 */
public interface BMPMatcher extends ImgMatcher<BMP>{
    public  List<Point> match(BMP bigImg, float similarity);
//    public  List<Point> match(BMP bigImg, SimilarStrategy strategy);
}
