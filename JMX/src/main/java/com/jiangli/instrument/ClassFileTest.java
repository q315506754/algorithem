package com.jiangli.instrument;

import com.jiangli.common.utils.PathUtil;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author Jiangli
 * @date 2019/1/23 15:07
 */
public class ClassFileTest {
    public static void main(String[] args) {
        System.out.println(PathUtil.getBaseProjectPath());
        System.out.println(PathUtil.getClassPath(ClassFileTest.class));
        System.out.println(PathUtil.getProjectPath(ClassFileTest.class));
        System.out.println(PathUtil.getSRC_JAVA_Path(ClassFileTest.class));
        System.out.println(PathUtil.getSRC_JAVA_Code_Path(ClassFileTest.class));
        System.out.println(PathUtil.getSRC_JAVA_Code_Path(ClassFileTest.class,"TestBean_Replaced.class"));
        System.out.println("----");
        //System.out.println(PathUtil.getClassFile(ClassFileTest.class,"ClassFileTest.class"));

        URL url = ClassLoader.getSystemResource("com/jiangli/instrument/TestBean.class");
        System.out.println(url);
        try {
            File file = new File(url.toURI());
            byte[] bytes = IOUtils.toByteArray(url);
            System.out.println(bytes.length);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
