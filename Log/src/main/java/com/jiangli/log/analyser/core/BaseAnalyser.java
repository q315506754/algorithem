package com.jiangli.log.analyser.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 * @date 2017/3/1 15:12
 */
public abstract class BaseAnalyser implements Analyser{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    protected Filter filter = new CommonLogFileFilter();
    protected Handler handler = new ConsolePrintHandler();

    protected Resource handlingResource;

    @Override
    public void analyse(Resource resource) {
        this.handlingResource = resource;

        logger.info("startAnalyse...");
        if (resource.exists()) {
            try {
                File file = resource.getFile();
                List<File> listIncludeChildren = getListIncludeChildren(file);

                for (File listIncludeChild : listIncludeChildren) {
                    if (filter.ignore(listIncludeChild)) {
                        continue;
                    }

                    handler.handle(listIncludeChild);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected List<File> getListIncludeChildren(File dirOrFile) {
        List<File> ret = new LinkedList<>();
        if (dirOrFile.isDirectory()) {
            File[] listFiles = dirOrFile.listFiles();
            for (File listFile : listFiles) {
                List<File> listIncludeChildren = getListIncludeChildren(listFile);
                ret.addAll(listIncludeChildren);
            }
        } else {
            ret.add(dirOrFile);
        }
        return ret;
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
