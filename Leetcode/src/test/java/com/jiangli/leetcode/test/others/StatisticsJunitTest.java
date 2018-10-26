package com.jiangli.leetcode.test.others;

import com.jiangli.junit.spring.RepeatFixedDuration;
import com.jiangli.junit.spring.RepeatFixedTimes;
import com.jiangli.junit.spring.StatisticsJunitRunner;
import com.jiangli.junit.spring.data.handler.PrintNothingDataHandler;
import com.jiangli.junit.spring.data.handler.ResultsCollector;
import com.jiangli.junit.spring.group.AvailableGroup;
import com.jiangli.junit.spring.group.InvokerGroup;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Administrator
 *
 *         CreatedTime  2016/9/1 0001 16:38
 */
@RunWith(StatisticsJunitRunner.class)
public class StatisticsJunitTest {
    double collec=1024;
    final int TIMES=100;
     int n=100;
     AtomicInteger N=new AtomicInteger(0);
    private Class<Object> aClass = Object.class;

    @RepeatFixedDuration
    @Test
    public void xxNew() {
        new Object();
    }

    @RepeatFixedTimes(thread = 10,value = 1000000)
    @Test
    public void xxNewT() {
        new Object();
    }
    @RepeatFixedTimes(thread = 1,value = 1000000)
    @Test
    public void xxNewT2() {
        new Object();
    }

    @RepeatFixedDuration(value = 5000,thread = 10)
    @Test
    public void xxNewTh() {
        new Object();
    }
    @RepeatFixedDuration(value = 5000,thread = 50)
    @Test
    public void xxNewTh2() {
        new Object();
    }

    @RepeatFixedDuration
    @Test
    public void xxPP() {
        n++;
    }

    @RepeatFixedDuration
    @Test
    public void xxPP2() {
        N.incrementAndGet();
    }

    @RepeatFixedDuration
    @Test
    public void xxts() {
       System.currentTimeMillis();
    }

    @RepeatFixedDuration
    @Test
    public void ndgts() {
       new Date().getTime();
    }

    @RepeatFixedDuration
    @Test
    public void cggts() {
       Calendar.getInstance().getTimeInMillis();
    }

    //781407
    @RepeatFixedDuration
    @Test
    public void sdff() {
        String yyyymmdd = new SimpleDateFormat("YYYYMMdd").format(new Date());
//        System.out.println(yyyymmdd);
    }

    //2713923
    @RepeatFixedDuration(value = 5000,thread = 50)
    @Test
    public void sdff22() {
        String yyyymmdd = new SimpleDateFormat("YYYYMMdd").format(new Date());
//        System.out.println(yyyymmdd);
    }

    @RepeatFixedTimes(thread = 1,value = 10000000)
    @Test
    public void sdfft_1() {
        String yyyymmdd = new SimpleDateFormat("YYYYMMdd").format(new Date());
//        System.out.println(yyyymmdd);
    }
    @RepeatFixedTimes(thread = 10,value = 10000000)
    @Test
    public void sdfft_2() {
        String yyyymmdd = new SimpleDateFormat("YYYYMMdd").format(new Date());
//        System.out.println(yyyymmdd);
    }

    @RepeatFixedTimes(value = 1000)
    @InvokerGroup(value = AvailableGroup.FIXED_LENGTH_STRING,params = {"100","500"})
    @Test
    public void sdfft_3(String str) {
        //System.out.println(str);
    }

    @RepeatFixedDuration
    @InvokerGroup(value = AvailableGroup.FIXED_LENGTH_STRING,params = {"100","500"})
    @Test
    public void sdfft_5(String str) {
        //System.out.println(str);
    }



    @RepeatFixedDuration(thread = 10)
    @InvokerGroup(value = AvailableGroup.FIXED_LENGTH_STRING,params = {"100","500"})
    @Test
    public void sdfft_6(String str) {
        //System.out.println(str);
    }

    @InvokerGroup(value = AvailableGroup.FIXED_LENGTH_STRING,params = {"100","500"})
    @Test
    public void sdfft_7(String str) {
        System.out.println(str);
    }

    @ResultsCollector(PrintNothingDataHandler.class)
    @InvokerGroup(value = AvailableGroup.FIXED_LENGTH_STRING,params = {"100","500"})
    @Test
    public void sdfft_8(String str) {
        System.out.println(str);
    }

    @RepeatFixedDuration
    @InvokerGroup(value = AvailableGroup.FIXED_LENGTH_STRING,params = {"100"})
    @Test
    public void sdfft_4(String str) {
        System.out.println(str);
    }

    @RepeatFixedDuration(thread = 10)
    @Test
    public void sdff33() {
        String yyyymmdd = new SimpleDateFormat("YYYYMMdd").format(new Date());
//        System.out.println(yyyymmdd);
    }

    @RepeatFixedDuration
    @Test
    public void calendar() {
        Calendar instance = Calendar.getInstance();
        StringBuilder sb = new StringBuilder();
        sb.append(instance.get(Calendar.YEAR));
        sb.append(instance.get(Calendar.MONTH)+1);
        sb.append(instance.get(Calendar.DATE));
        //String s = sb.toString();
//        System.out.println(s);
    }

    @RepeatFixedDuration
    @Test
    public void xxNew2() {
        try {
            aClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void func() {
        String s="a"+"b"+"c"+"d";

        StringBuilder sb = new StringBuilder();
        String a = "a";
        String b = "b";
        String c = "c";
        String d = "d";
        String s2 = a+b+c+d;
        Assert.assertTrue(true);
    }

    @RepeatFixedDuration
    @Test
    public void func2222() {
        syncMethod();
    }
    @RepeatFixedDuration
    @Test
    public void func2222_4444() {
        syncObject();
    }
    @RepeatFixedDuration
    @Test
    public void func2222_33() {
        syncMethodStatic();
    }

    @RepeatFixedDuration
    @Test
    public void func333() {
        smethod();
    }

    @RepeatFixedDuration
    @Test
    public void func_calc_1() {
        for (int i = 0; i < TIMES; i++) {
            collec= 1024 *collec;
        }
    }

    @RepeatFixedDuration
    @Test
    public void func_calc_2() {
        for (int i = 0; i < TIMES; i++) {
            collec= collec/1024;
        }
    }

    @RepeatFixedDuration
    @Test
    public void func_calc_3() {
        for (int i = 0; i < TIMES; i++) {
            collec= collec + 1024;
        }
    }

    @RepeatFixedDuration
    @Test
    public void func_calc_5() {
        for (int i = 0; i < TIMES; i++) {
            collec= collec % 1024;
        }
    }

    @RepeatFixedDuration
    @Test
    public void func_calc_4() {
        for (int i = 0; i < TIMES; i++) {
            collec= ((int)collec << 10);
        }
    }

    private void execute(Runnable cmd) {

    }

    @RepeatFixedDuration
    @Test
    public void func_call_1() {
        execute(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    @RepeatFixedDuration
    @Test
    public void func_call_2() {
        execute(() -> {

        });
    }

    @RepeatFixedDuration
    @Test
    public void func_io() {
        try {
            StringBuilder sb = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("pom.xml")));
            String readLine = bufferedReader.readLine();
            while (readLine!=null) {
                sb.append(readLine);
                sb.append("\n");
                readLine = bufferedReader.readLine();
            }
//            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RepeatFixedDuration
    @Test
    public void funcmd5() {
        String msg = "123456";
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            byte[] digest = md5.digest(msg.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                String str = Integer.toHexString(b & 0xff);
                if (str.length()==1) {
                    str = "0"+str;
                }
                sb.append(str);
            }
            String s = sb.toString();
//            System.out.println(s);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public synchronized void syncMethod() {

    }

    public  void syncObject() {
        synchronized (this) {

        }
    }

    public static synchronized void syncMethodStatic() {

    }

    public  void smethod() {

    }

}
