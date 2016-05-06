package com.jiangli.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * cd C:\coolyProject\DatabaseRunner\target
 * java -jar DatabaseRunner.jar
 */

public final class PropertiesUtil {
    private static String[] propsList = new String[]{"classpath*:application.properties", "classpath*:props/*.properties", "classpath*:db/*.properties"};
    public static final List<Props> props = new ArrayList<Props>();
    private static final Log logger = LogFactory.getLog("Runner");
    private static final PathMatcher pathMatcher = new AntPathMatcher();
    static {
        for (String s : propsList) {
            logger.debug("loading:" + s);
            try {
                PathMatchingResourcePatternResolver p = new PathMatchingResourcePatternResolver();
                Resource[] resources = p.getResources(s);
                logger.debug("length:" + resources.length);
                for (Resource resource : resources) {
                    boolean jarURL = ResourceUtils.isJarURL(resource.getURL());
                    logger.debug(s + "--" + resource.getFilename() + " in Jar?" + jarURL);
                    URLConnection con = resource.getURL().openConnection();
                    logger.debug("con:" + con.getClass());

                    if (con instanceof JarURLConnection) {
                        logger.debug("JarURLConnection.......");
                        // Should usually be the case for traditional JAR files.
                        JarURLConnection jarCon = (JarURLConnection) con;
                        JarFile jarFile = jarCon.getJarFile();
                        JarEntry jarEntry = jarCon.getJarEntry();
                        addFile(jarFile.getInputStream(jarEntry), resource.getFilename());
                    } else {
                        logger.debug("common file connection.......");
                        File file = resource.getFile();
                        addFile(file);
                    }


                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                logger.error("loading:" + s + " error..." + e.getMessage());
            }
            logger.debug(".................................");
        }

    }


    private static void addFile(InputStream in, String fileName) throws IOException {
        Props one = new Props();

        Properties properties = new Properties();
        properties.load(in);
        in.close();

        one.setProperties(properties);
        one.setFileName(fileName);

        props.add(one);

    }

    private static void addFile(File file) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        addFile(in, file.getName());

    }

    public static String readStringValue(String key) {
        for (Props prop : props) {
            String property = prop.getProperties().getProperty(key);
            if (property != null) {
                return property;
            }
        }
        return null;
    }

    public static int readIntValue(String key) {
        return Integer.parseInt(readStringValue(key));
    }

    public static String[] readStringArray(String key, String regex) {
        String val = readStringValue(key);
        String[] result = val.split(regex);
        return result;
    }

    public static List<Props> getProps() {
        return props;
    }
}
