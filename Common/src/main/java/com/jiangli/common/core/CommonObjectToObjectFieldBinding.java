package com.jiangli.common.core;

import com.jiangli.common.utils.MethodUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/7 0007 11:07
 */
public class CommonObjectToObjectFieldBinding implements  ObjectToObjectFieldBinding{
    protected Logger logger = LoggerFactory.getLogger(this.getClass());
    private Object src;
    private String srcField;
    private Object dest;
    private String destField;
    private ValueDecorator valueDecorator;

    public CommonObjectToObjectFieldBinding(Object src, String srcField, Object dest, String destField) {
        this.src = src;
        this.srcField = srcField;
        this.dest = dest;
        this.destField = destField;
    }

    public CommonObjectToObjectFieldBinding(Object src, String srcField, Object dest, String destField, ValueDecorator valueDecorator) {
        this.src = src;
        this.srcField = srcField;
        this.dest = dest;
        this.destField = destField;
        this.valueDecorator = valueDecorator;
    }

    @Override
    public void refresh() {
        try {
            logger.debug("refresh called~");
            Object srcVal = MethodUtil.invokeGetter(src, srcField);
            logger.debug("srcVal:"+srcVal);
            if (valueDecorator != null) {
                srcVal = valueDecorator.decorate(srcVal);
                logger.debug("decorated srcVal:"+srcVal);
            }
            MethodUtil.invokeSetter(dest, destField, srcVal);
            logger.debug("invoke over...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
