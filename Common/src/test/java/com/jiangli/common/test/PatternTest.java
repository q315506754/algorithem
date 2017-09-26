package com.jiangli.common.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/4/26 0026 17:21
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:NONE"})
public class PatternTest {
    private static Logger logger = LoggerFactory.getLogger(PatternTest.class);

    @Test
    public void func() {
       String name = "aaa";
        String newName = name + "(123)";
//        System.out.println(newName);

        String each = newName.substring(name.length());
        System.out.println(each);

        Pattern compile = Pattern.compile("(?<=\\()\\d+(?=\\))");
//        Pattern compile = Pattern.compile("\\(\\\\d+\\)");
        Matcher matcher = compile.matcher(each);
        matcher.find();
        System.out.println(matcher.group());

        Pattern pattern = Pattern.compile("\\s*[,]+\\s*");
        System.out.println(Arrays.toString(pattern.split(" 111, 333")));
        System.out.println( Arrays.toString(pattern.split(" [111, 333,23]")));

        System.out.println(String.format("\n%s extension = (%<s)%s.getExtensionLoader(%s.class).getExtension(extName);",
                "A", PatternTest.class.getSimpleName(), "A"));
//        System.out.println(String.format("\n%s extension = (%>s)%s.getExtensionLoader(%s.class).getExtension(extName);",
//                "A", PatternTest.class.getSimpleName(), "A"));
    }



}
