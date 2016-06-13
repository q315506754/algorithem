package com.jiangli.common.test;

import com.jiangli.common.utils.DateUtil;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/12 0012 16:46
 */
public class DateUtilTest {
    @Test
    public void func() {
        Date date = new Date();
        System.out.println(DateUtil.getDate_YYYY_MM_DD(date.getTime()));

        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.DATE,1);
        instance.set(Calendar.HOUR_OF_DAY,0);
        instance.set(Calendar.MINUTE,0);
        instance.set(Calendar.SECOND,0);

        System.out.println(DateUtil.getDate_YYYY_MM_DD(instance.getTimeInMillis()));

        instance.set(Calendar.DATE,instance.getActualMaximum(Calendar.DATE));
        instance.set(Calendar.HOUR_OF_DAY,instance.getActualMaximum(Calendar.HOUR_OF_DAY));
        instance.set(Calendar.MINUTE,instance.getActualMaximum(Calendar.MINUTE));
        instance.set(Calendar.SECOND,instance.getActualMaximum(Calendar.SECOND));
        System.out.println(DateUtil.getDate_YYYY_MM_DD(instance.getTimeInMillis()));

    }
}
