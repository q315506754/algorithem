package com.jiangli.common.test;

import com.jiangli.common.utils.PathUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:applicationContext.xml"})
public class PathUtilTest {
    private static Logger logger = LoggerFactory.getLogger(PathUtilTest.class);

    @Test
    public void func() {
       System.out.println(PathUtil.getClassPath());
       System.out.println(PathUtil.getBaseProjectPath());
        PathUtil.ProjectPath jni = PathUtil.getProjectPath("JNI");
        System.out.println(jni);
        System.out.println(jni.getPath(PathUtil.PATH_SRC_MAIN_RESOURCES));
    }



}
