package com.jiangli.log.analyser.core;

import java.io.File;

/**
 * @author Jiangli
 * @date 2017/3/1 15:24
 */
public interface Filter {
    boolean ignore(File file);
}
