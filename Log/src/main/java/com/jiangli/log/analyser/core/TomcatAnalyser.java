package com.jiangli.log.analyser.core;

import org.springframework.core.io.Resource;

/**
 * @author Jiangli
 * @date 2017/3/1 9:12
 */
public class TomcatAnalyser extends   BaseAnalyser{
    private ExceptionOutputHandler handler;
    public TomcatAnalyser(String outputName) {
        handler = new ExceptionOutputHandler(outputName);
        this.setHandler(handler);
    }

    @Override
    public void analyse(Resource resource) {
        super.analyse(resource);

        handler.outputResults();
    }
}
