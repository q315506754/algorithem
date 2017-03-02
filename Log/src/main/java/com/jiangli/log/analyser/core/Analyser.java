package com.jiangli.log.analyser.core;

import org.springframework.core.io.Resource;

/**
 * @author Jiangli
 * @date 2017/3/1 9:09
 */
public interface Analyser {
    void analyse(Resource resource);
}
