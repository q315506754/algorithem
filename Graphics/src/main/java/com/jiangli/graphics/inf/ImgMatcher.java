package com.jiangli.graphics.inf;

import com.jiangli.graphics.common.Point;

import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/3 0003 10:24
 */
public interface ImgMatcher<T> {
    public  List<Point> match(T bigImg,float similarity);
}
