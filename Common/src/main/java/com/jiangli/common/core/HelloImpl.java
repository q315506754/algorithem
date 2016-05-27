package com.jiangli.common.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/5/27 0027 9:48
 */
public class HelloImpl implements IHello{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public String sayHello(String yourName) {
        logger.debug("invoked,name:"+yourName);
        String ret = "Hi "+yourName+"! I am JL,How are your?";
        logger.debug("said:"+ret);
        return ret;
    }
}
