package com.jiangli.spring.test;

import org.junit.Test;
import org.springframework.beans.factory.xml.DefaultNamespaceHandlerResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Administrator
 *
 *         CreatedTime  2016/7/29 0029 16:23
 */
public class PropertiesLoaderUtilsTest {
    @Test
    public void func() throws  Exception{
        Properties mappings =
                PropertiesLoaderUtils.loadAllProperties(DefaultNamespaceHandlerResolver.DEFAULT_HANDLER_MAPPINGS_LOCATION, ClassUtils.getDefaultClassLoader());
        System.out.println(mappings);

        Map<String, Object> handlerMappings = new ConcurrentHashMap<String, Object>(mappings.size());
        CollectionUtils.mergePropertiesIntoMap(mappings, handlerMappings);
        System.out.println(handlerMappings);
    }

}
