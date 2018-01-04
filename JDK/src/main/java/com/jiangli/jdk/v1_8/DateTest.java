package com.jiangli.jdk.v1_8;

import java.time.*;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;

/**
 * @author Jiangli
 * @date 2018/1/4 18:37
 */
public class DateTest {
    public static void main(String[] args) {
        // 取当前日期：
        LocalDate today = LocalDate.now(); // -> 2014-12-24
// 根据年月日取日期：
        LocalDate crischristmas = LocalDate.of(2014, 12, 25); // -> 2014-12-25
// 根据字符串取：
        LocalDate endOfFeb = LocalDate.parse("2014-02-28"); // 严格按照ISO yyyy-MM-dd验证，02写成2都不行，当然也有一个重载方法允许自己定义格式
        //LocalDate.parse("2014-02-29"); // 无效日期无法通过：DateTimeParseException: Invalid date

        // 取本月第1天：
        LocalDate firstDayOfThisMonth = today.with(TemporalAdjusters.firstDayOfMonth()); // 2017-03-01
        System.out.println("firstDayOfThisMonth:"+firstDayOfThisMonth);
// 取本月第2天：
        LocalDate secondDayOfThisMonth = today.withDayOfMonth(2); // 2017-03-02
        System.out.println("secondDayOfThisMonth:"+secondDayOfThisMonth);
// 取本月最后一天，再也不用计算是28，29，30还是31：
        LocalDate lastDayOfThisMonth = today.with(TemporalAdjusters.lastDayOfMonth()); // 2017-12-31
        System.out.println("lastDayOfThisMonth:"+lastDayOfThisMonth);
// 取下一天：
        LocalDate firstDayOf2015 = lastDayOfThisMonth.plusDays(1); // 变成了2018-01-01
        System.out.println("firstDayOf2015:"+firstDayOf2015);
// 取2017年1月第一个周一，用Calendar要死掉很多脑细胞：
        LocalDate firstMondayOf2015 = LocalDate.parse("2018-01-01").with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY)); // 2017-01-02
        System.out.println("firstMondayOf2015:"+firstMondayOf2015);

        LocalTime now = LocalTime.now(); // 23:11:08.006
        System.out.println(now);
        LocalTime zero = LocalTime.of(0, 0, 0); // 00:00
        System.out.println(zero);
        System.out.println(LocalTime.of(11, 22, 33));
        //System.out.println(LocalTime.of(24, 22, 33));// Invalid value for HourOfDay (valid values 0 - 23): 24
        LocalTime mid = LocalTime.parse("12:00:00"); // 12:00:00
        System.out.println(mid);


        LocalDateTime sylvester = LocalDateTime.of(2014, Month.DECEMBER, 31, 23, 59, 59);
        System.out.println(sylvester);
        DayOfWeek dayOfWeek = sylvester.getDayOfWeek();
        System.out.println(dayOfWeek);      // WEDNESDAY
        Month month = sylvester.getMonth();
        System.out.println(month);          // DECEMBER
        long minuteOfDay = sylvester.getLong(ChronoField.MINUTE_OF_DAY);
        System.out.println(minuteOfDay);    // 1439
    }

}
