package com.jiangli.test;

import com.jiangli.junit.MyCountTimeRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

/**
 * @author Administrator
 *
 *         CreatedTime  2016/9/1 0001 16:38
 */
public class JunitTestRule {
    @Rule
    public TemporaryFolder testRule = new TemporaryFolder();

    @Rule
    public MyCountTimeRule myRule = new MyCountTimeRule();

    @Test
    public void func() {
        File root = testRule.getRoot();
        System.out.println(root);
    }

    @Test
    public void func2() {
        File root = testRule.getRoot();
        System.out.println(root);
    }

}
