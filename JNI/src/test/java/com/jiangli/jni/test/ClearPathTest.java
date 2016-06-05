package com.jiangli.jni.test;

import com.jiangli.common.core.FileCopyProcesser;
import com.jiangli.common.core.FileStringRegexProcesser;
import com.jiangli.common.utils.FileUtil;
import com.jiangli.common.utils.PathUtil;
import com.jiangli.jni.common.Config;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/3 0003 13:55
 */
public class ClearPathTest {
    @Test
    public void testRemove() {
        String capturePath = PathUtil.buildPath(PathUtil.getProjectPath("JNI").getProjectBasePath(), PathUtil.PATH_SRC_MAIN_RESOURCES, "findsmile", "captured");
        List<String> capturePaths = FileUtil.getFilePathFromDirPath(capturePath);

    }

    @Test
    public void func2() throws Exception {
//        Class cls = Config.class;
        Class cls = PathUtil.class;
        URI uri = cls.getClassLoader().getResource("").toURI();
        String path = uri.getPath();
        System.out.println(path);

        System.out.println(PathUtil.getProjectPath(Config.class));
        System.out.println(PathUtil.getSRC_JAVA_Path(Config.class));
        String src_java_code_path = PathUtil.getSRC_JAVA_Code_Path(Config.class);
        System.out.println(src_java_code_path);
        File file = new File(src_java_code_path);
        System.out.println(FileUtil.getPrefix(file));
        System.out.println(FileUtil.getSuffix(file));

        File process = FileUtil.process(file, new FileCopyProcesser());
        System.out.println(process);

        File process1 = FileUtil.process(file, new FileStringRegexProcesser("int\\s*test_hWnd\\s*=\\s*\\d*;", "int test_hWnd=33333;"));
        System.out.println(process1);
//        FileUtil.openFile(process1);

        File  process2 = FileUtil.process(process1, new FileStringRegexProcesser("int\\s*test_hWnd\\s*=\\s*\\d*;", "int test_hWnd=44444;"));
        System.out.println(process2);
//        FileUtil.openFile(process2);


//        2819462
        File  process3 = FileUtil.processAndReplace(file, new FileStringRegexProcesser("int\\s*test_hWnd\\s*=\\s*\\d*;", "int test_hWnd=55555;"));
        System.out.println(process3);
        FileUtil.openFile(process3);
    }
}
