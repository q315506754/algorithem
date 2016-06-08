package com.jiangli.jni.app.richman9.smallgame.clickpoints;

import com.jiangli.common.utils.ArrayUtil;
import com.jiangli.common.utils.RandomUtil;
import com.jiangli.graphics.common.BMP;
import com.jiangli.graphics.common.Color;
import com.jiangli.graphics.common.Point;
import com.jiangli.graphics.inf.BMPMatcher;
import com.jiangli.jni.app.richman9.smallgame.PointColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/8 0008 16:27
 */
public class ClickPointsBMPMatcher implements BMPMatcher {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private int type_2_2 = 0;
    private int type_3_3 = 1;
    private int type_4_4 = 2;
    private PointFeatures[] features ;
    private Point offset ;
    private Point[][] checkPoints  = new Point[3][] ;
    private PointLayout[] layouts;

    public static class PointLayout{
        Point firstOffset;
        Point cellInterval;
        Point cell;

        public PointLayout() {
        }

        public PointLayout(Point firstOffset, Point cellInterval, Point cell) {
            this.firstOffset = firstOffset;
            this.cellInterval = cellInterval;
            this.cell = cell;
        }
    }

    public ClickPointsBMPMatcher() {
        init();
    }

    public ClickPointsBMPMatcher(Point offset) {
        this.offset = offset;
        init();
    }

    public void init() {
        // features init
        int startIdx = type_2_2;
        int endIdx = type_4_4;
        int totalIdx = endIdx - startIdx + 1;
        features = new PointFeatures[totalIdx];
        ArrayUtil.init(features,PointFeatures.class);

        features[type_2_2].addFeature(setPointOffSet(new Point(250,220)),new Color(86,164,119));
        features[type_2_2].addFeature(setPointOffSet(new Point(240,220)),new Color(85,162,118));
        features[type_2_2].addFeature(setPointOffSet(new Point(230,220)),new Color(84,159,116));

        features[type_3_3].addFeature(setPointOffSet(new Point(220,220)),new Color(82,158,114));
        features[type_3_3].addFeature(setPointOffSet(new Point(210,220)),new Color(80,155,111));

        // checkPoints init
        layouts = new PointLayout[totalIdx];
        ArrayUtil.init(layouts,PointLayout.class);

        layouts[type_2_2].firstOffset = setPointOffSet(new Point(258,85));
        layouts[type_2_2].cellInterval = new Point(8,8);
        layouts[type_2_2].cell = new Point(110,110);

        layouts[type_3_3].firstOffset = setPointOffSet(new Point(232,56));
        layouts[type_3_3].cellInterval = new Point(8,8);
        layouts[type_3_3].cell =  new Point(92,92);

        layouts[type_4_4].firstOffset = setPointOffSet(new Point(206,29));
        layouts[type_4_4].cellInterval = new Point(8,8);
        layouts[type_4_4].cell =  new Point(80,80);

        // checkPoints init
        for (int i = startIdx; i <= endIdx; i++) {
            int grid = getGrid(i);
            int cells = grid * grid;
            checkPoints[i] = new Point[cells];

            logger.debug("i:"+i+" grid:"+grid + " cells:"+cells);
            PointLayout layout = layouts[i];

            for(int m=0;m<grid;m++) {
                int y = layout.firstOffset.getY() + m * layout.cellInterval.getY()+ m * layout.cell.getY()+layout.cell.getY()/2;
                for(int n=0;n<grid;n++) {
                    int x = layout.firstOffset.getX() + n * layout.cellInterval.getX()+ n * layout.cell.getX()+layout.cell.getX()/2;
                    checkPoints[i][m*grid+n] = new Point(x, y);
                }
            }

            logger.debug("checkPoints["+i+"]:"+Arrays.toString(checkPoints[i]));
        }



    }

    private int getGrid(int gridType) {
        return gridType+2;
    }

    public Point setPointOffSet(Point orgPoint) {
        if (this.offset != null) {
            orgPoint.setX(orgPoint.getX() + offset.getX());
            orgPoint.setY(orgPoint.getY() + offset.getY());
        }
        return orgPoint;
    }

    public int getGridType(BMP bigImg) {
        for(int idx = type_2_2;idx <= type_3_3 ;idx++){
            PointFeatures feature = features[idx];

            List<PointFeature> featuresList = feature.getFeatures();
            boolean allPass = true;
            for (PointFeature pointFeature : featuresList) {
                Color colorObj = bigImg.getColorObj(pointFeature.getPoint().getX(), pointFeature.getPoint().getY());
                //cmp
                if (!colorObj.equals(pointFeature.getColor())) {
                    allPass = false;
                    break;
                }
            }

            if (allPass) {
                return idx;
            }

        }
        return type_4_4;
    }

    public List<PointColor> getPointColor(BMP bigImg,int gridType) {
        Point[] checkPoint = checkPoints[gridType];

        List<PointColor> ret = new ArrayList<>(checkPoint.length);

        int colorIdx = 0;
        for (Point eachCheckPoint : checkPoint) {
            try {
                //important
                //because may be altered later...
                Point clone = eachCheckPoint.clone();

                PointColor pointColor = new PointColor(clone,bigImg.getColorObj(clone));
                ret.add(pointColor);

                //set type
                for (PointColor existPointColor : ret) {
                    if (pointColor.getColor().equals(existPointColor.getColor())) {
                        pointColor.setType(existPointColor.getType());
                    }
                }

                if (pointColor.getType() < 0) {
                    pointColor.setType(colorIdx++);
                }
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }

        return ret;
    }

    public void retainMaxFrequencePointColor(List<PointColor> list, int gridType) {
        int grid = getGrid(gridType);
        int maxTypes = grid * grid;
        int[]  fre= new int[maxTypes];
        ArrayUtil.init(fre,-1);

        for (PointColor pointColor : list) {
            fre[pointColor.getType()]++;
        }


        //calc maxTypeFre's type
        int maxType = 0;
        int maxTypeFre = fre[maxType];

        for (int i = 0; i < fre.length; i++) {
            int curFreq = fre[i];

            if (curFreq > maxTypeFre) {
                maxType = i;
                maxTypeFre = curFreq;
            }
        }


        //retain
        Iterator<PointColor> iterator = list.iterator();
        while (iterator.hasNext()) {
            PointColor next = iterator.next();
            if (next.getType() != maxType) {
                iterator.remove();
            }
        }

    }

    @Override
    public List<Point> match(BMP bigImg, float similarity) {
        List<Point> points = new LinkedList<>();
        int gridType = getGridType(bigImg);
        logger.debug("cur file:"+bigImg.getFile());
        logger.debug("gridType:"+gridType);



        List<PointColor> pointColor = getPointColor(bigImg, gridType);
        logger.debug("pointColor:"+pointColor);

        retainMaxFrequencePointColor(pointColor,gridType);
        logger.debug("pointColor after retained:"+pointColor);

//        Point[] checkPoint = checkPoints[gridType];
//        for (Point point : checkPoint) {
//            points.add(point);
//        }
        PointColor randomOne = RandomUtil.getRandomOne(pointColor);
//        PointColor randomOne = pointColor.get(0);
        logger.debug("randomOne:"+randomOne);

        points.add(randomOne.getPoint());


//        for (PointColor color : pointColor) {
//            points.add(color.getPoint());
//        }

        return points;
    }
}
