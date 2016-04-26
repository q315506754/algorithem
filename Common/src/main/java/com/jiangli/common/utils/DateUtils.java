package com.jiangli.common.utils;

import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DateUtils {
    public Date minusDay(Date date, int count) {
        return new Date(date.getTime() - 0x5265c00L * (long) count);
    }

    public String getDate_YYYYMMDD2(long timeStamp) {
        return (new SimpleDateFormat("MM月dd日")).format(timeStamp);
    }

    public Long getDate_yyyyMMdd(String date) throws ParseException {
        long timeStamp = 0;
        Date formatDate;
        SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd");
        formatDate = t.parse(date);
        timeStamp = formatDate.getTime();
        return timeStamp;
    }

    //将yyyy-MM-dd HH:mm格式的日期时间字符串转成Long
    public Long getTimeStamp_yyyyMMddHHmmToLong(String dateAndTime) throws ParseException {
        long timeStamp = 0;
        Date formatDate;
        SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formatDate = t.parse(dateAndTime);
        timeStamp = formatDate.getTime();
        return timeStamp;
    }

    public String getDate_YYYY_MM_DD(long timeStamp) {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(timeStamp);
    }

    public String getTime_HH_mm_ss(long timeStamp) {
        return (new SimpleDateFormat("HH:mm:ss")).format(timeStamp);
    }

    public String getDate_YYYYMMDD(long timeStamp) {

        return (new SimpleDateFormat("yyyy-MM-dd")).format(timeStamp);
    }

    public String getDate_YYYYMMDD_Chinese(long timeStamp) {

        return (new SimpleDateFormat("yyyy年MM月dd日")).format(timeStamp);
    }

    public String getCurrentDate() {
        return (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
    }

    public String getCurrentDate_YMDHms() {
        return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(new Date());
    }

    public String getCurrentYearMonth() {
        return (new SimpleDateFormat("yyyyMM")).format(new Date());
    }

    public String getCurrentYear() {
        return (new SimpleDateFormat("yyyy")).format(new Date());
    }

    public String getCurrentDate(String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(new Date());
    }

    public String getCurrentTime() {
        return (new SimpleDateFormat("HH:mm:ss")).format(new Date());
    }

    public String getCurrentTime(String format) {
        SimpleDateFormat t = new SimpleDateFormat(format);
        return t.format(new Date());
    }

    public int getDayOfWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.get(7);
    }

    public int getDayOfWeek(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(7);
    }

    public int getDayOfWeek(long date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        int t = cal.get(7);
        return t;
    }

    public int getDayOfWeek2(long date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        int t = cal.get(7);
        if (t == 1) {
            t = 7;
        } else {
            t--;
        }
        return t;
    }

    public int getDayOfMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(5);
    }

    public int getDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(5);
    }

    public int getMaxDayOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getActualMaximum(5);
    }

    public int getDayOfYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(6);
    }

    public int getDayOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(6);
    }

    public String getDayOfWeekCN(long date) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        int n = cal.get(7);
        if (n == 1) {
            return "星期天";
        } else if (n == 2) {
            return "星期一";
        } else if (n == 3) {
            return "星期二";
        } else if (n == 4) {
            return "星期三";
        } else if (n == 5) {
            return "星期四";
        } else if (n == 6) {
            return "星期五";
        } else {
            return "星期六";
        }
    }

    public boolean isTime(String time) {
        try {
            String arr[];
            arr = time.split(":");
            if (arr.length < 2)
                return false;
            int h;
            int m;
            int s;
            h = Integer.parseInt(arr[0]);
            m = Integer.parseInt(arr[1]);
            s = 0;
            if (arr.length == 3)
                s = Integer.parseInt(arr[2]);
            return h >= 0 && h <= 23 && m >= 0 && m <= 59 && s >= 0 && s <= 59;
        } catch (Exception e) {
            return false;
        }

    }

    public boolean isDate(String date) {
        try {
            String arr[];
            arr = date.split("-");
            if (arr.length < 3)
                return false;
            int y;
            int m;
            int d;
            y = Integer.parseInt(arr[0]);
            m = Integer.parseInt(arr[1]);
            d = Integer.parseInt(arr[2]);
            return y >= 0 && m <= 12 && m >= 0 && d >= 0 && d <= 31;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int t = cal.get(7);
        return t == 7 || t == 1;
    }

    public Date addMinute(Date date, int count) {
        return new Date(date.getTime() + 60000L * (long) count);
    }

    public Date addHour(Date date, int count) {
        return new Date(date.getTime() + 0x36ee80L * (long) count);
    }

    public Date addDay(Date date, int count) {
        return new Date(date.getTime() + 0x5265c00L * (long) count);
    }

    public Date addWeek(Date date, int count) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(3, count);
        return c.getTime();
    }

    public Date addMonth(Date date, int count) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(2, count);
        return c.getTime();
    }

    public Date addYear(Date date, int count) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(1, count);
        return c.getTime();
    }

    public final String Format_Date = "yyyy-MM-dd";
    public final String Format_Time = "HH:mm:ss";
    public final String Format_DateTime = "yyyy-MM-dd HH:mm:ss";

    // 获取N个月后的日期
    public String getMonthAfter(int month) {
        Calendar cal = Calendar.getInstance();
        cal.set(2, cal.get(2) + month);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());

    }

    // 获取N天后的日�?
    public String getDayAfter(int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(6, cal.get(6) + day);
        return (new SimpleDateFormat("yyyy-MM-dd")).format(cal.getTime());
    }

    //
    public long getTimeStamp(String date) throws ParseException {
        long timeStamp = 0;
        Date formatDate;
        SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd");
        formatDate = t.parse(date);
        timeStamp = formatDate.getTime();
        return timeStamp;
    }

    public Date getDate(String date) throws ParseException {

        Date formatDate;
        SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatDate = t.parse(date);
        return formatDate;
    }

    public Date getNextDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    //String(yyyy-MM-dd) date --> Date
    public Date getDate_yyyy_MM_dd(String date) throws ParseException {

        Date formatDate;
        SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd");
        formatDate = t.parse(date);
        return formatDate;
    }

    public long getDateYYMMDD(String date) throws ParseException {

        Date formatDate;
        SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd");
        formatDate = t.parse(date);
        ////System.out.println("fomatDate====" + formatDate);
        ////System.out.println("timeStamp====" + formatDate.getTime());
        return formatDate.getTime();
    }

    public long getTimeStampHHmmss(String date) throws ParseException {

        Date formatDate;
        SimpleDateFormat t = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatDate = t.parse(date);
        ////System.out.println("fomatDate====" + formatDate);
        ////System.out.println("timeStamp====" + formatDate.getTime());
        return formatDate.getTime();
    }

    public long getCurrentMonth() throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.set(5, 1);

        return getDateYYMMDD(getDate_YYYYMMDD(cal.getTimeInMillis()));
    }

    public int getDay(long startDate, long endDate) {

        long time = endDate - startDate;
        ////System.out.println("date=====" + time);
        if (time < 0) {
            return 0;
        } else {
            if (time % 86400000 == 0) {
                return (int) (time / 86400000);
            } else {
                return (int) (time / 86400000 + 1);
            }
        }

    }

    public int getDay(String endDate) throws ParseException {
        long time = getDateYYMMDD(endDate);
        return getDay(System.currentTimeMillis(), time);
    }


    /**
     * 根据传过来的间隔时间生成表达式
     *
     * @param stepTime
     *
     * @return 返回表达式
     */
    public String getCronExpression(Long stepTime) {
        String cronExpression = "";
        if (stepTime < 60) {
            cronExpression = "0/" + stepTime + " * * * * ?";
        } else if (stepTime >= 60 && stepTime < 60 * 60) {
            cronExpression = "* 0/" + ((double) stepTime / 60) + " * * * ?";
        } else if (stepTime >= 60 * 60 && stepTime < 60 * 60 * 24) {
            cronExpression = "* * 0/" + ((double) stepTime / (60 * 60)) + " * * ?";
        } else if (stepTime >= 60 * 60 * 24 && stepTime < 60 * 60 * 24 * 31) {
            cronExpression = "* * * 0/" + ((double) stepTime / (60 * 60 * 24 * 31)) + " * ?";
        }
        return cronExpression;
    }


    /**
     * 获取当前日期的月份
     *
     * @param date
     *
     * @return
     *
     * @throws java.text.ParseException
     */
    public int getMonth(long date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前日期的年份
     *
     * @param date
     *
     * @return
     *
     * @throws java.text.ParseException
     */
    public int getYear(long date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前日期的天
     *
     * @param date
     *
     * @return
     *
     * @throws java.text.ParseException
     */
    public int getDay(long date) throws ParseException {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date);
        return cal.get(Calendar.DATE);
    }

    /**
     * 在指定时间段中获取指定周几的日期
     *
     * @param startTime
     * @param endTime
     * @param week
     *
     * @return
     *
     * @throws Exception
     */
    public List<Long> getWeekWithInTimes(Long startTime, Long endTime, int week) throws Exception {
        Calendar c_begin = new GregorianCalendar();
        Calendar c_end = new GregorianCalendar();

        int year1 = getYear(startTime);
        int month1 = getMonth(startTime);
        int day1 = getDay(startTime);
        int year2 = getYear(endTime);
        int month2 = getMonth(endTime);
        int day2 = getDay(endTime);

        c_begin.set(year1, month1 - 1, day1); //Calendar的月从0-11，所以4月是3.
        c_end.set(year2, month2 - 1, day2); //Calendar的月从0-11，所以5月是4.

        c_end.add(Calendar.DAY_OF_YEAR, 1);  //结束日期下滚一天是为了包含最后一天
        List<Long> list = new ArrayList<Long>();

        while (c_begin.before(c_end)) {
            if (c_begin.get(Calendar.DAY_OF_WEEK) == week) {
                Long datetime = getDate_yyyyMMdd(getDate_YYYYMMDD(c_begin.getTime().getTime()));
                list.add(datetime);
            }
            c_begin.add(Calendar.DAY_OF_YEAR, 1);
        }
        return list;
    }
}
