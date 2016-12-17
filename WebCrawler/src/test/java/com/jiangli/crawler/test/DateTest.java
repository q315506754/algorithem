package com.jiangli.crawler.test;

import com.jiangli.common.utils.DateUtil;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by Jiangli on 2016/12/16.
 */
public class DateTest {

    @Test
    public void func() throws ParseException {
        String s = "2015-1-28";
        Date date_yyyy_mm_dd = DateUtil.getDate_yyyy_MM_dd(s);
        System.out.println(date_yyyy_mm_dd);


    }
}
