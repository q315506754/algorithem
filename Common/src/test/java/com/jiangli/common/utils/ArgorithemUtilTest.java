package com.jiangli.common.utils;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Jiangli
 * @date 2017/7/17 16:25
 */
public class ArgorithemUtilTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void isPowerOf2() throws Exception {
        Assert.assertTrue(ArgorithemUtil.isPowerOf2(1024));
        Assert.assertTrue(ArgorithemUtil.isPowerOf2(512));
        Assert.assertFalse(ArgorithemUtil.isPowerOf2(111));
    }

}