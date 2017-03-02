package com.jiangli.log.analyser.feature;

/**
 * @author Jiangli
 * @date 2017/3/1 15:47
 */
public interface Feature {
    boolean featureStarts(String line);
    boolean featureEnds(String line);
}
