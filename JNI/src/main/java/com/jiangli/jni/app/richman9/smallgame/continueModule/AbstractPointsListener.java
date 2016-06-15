package com.jiangli.jni.app.richman9.smallgame.continueModule;

import com.jiangli.jni.app.richman9.smallgame.PointsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jiangli on 2016/6/15.
 */
public abstract class AbstractPointsListener implements PointsListener {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
}
