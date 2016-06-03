package com.jiangli.common.utils;

import org.apache.commons.lang.StringUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/3 0003 11:45
 */
public class TimeAnalyser {
    private List<TimeCol> data = new LinkedList<>();
    private String title;
    private long startTs = System.currentTimeMillis();

    public void setTitle(String title) {
        this.title = title;
    }

    public TimeAnalyser() {
    }

    public TimeAnalyser(String title) {
        this.title = title;
    }

    public void push( String desc) {
        TimeCol one = new TimeCol();
        one.ts = System.currentTimeMillis();
        one.desc = desc;
        data.add(one);
    }

    public void analyse( ) {
        System.out.println("-----------------------");
        if (!StringUtils.isEmpty(title)) {
            System.out.println(title);
        }
        long lastTs=startTs;
        for (int i = 0; i < data.size(); i++) {
            TimeCol cur = data.get(i);
            long prevTs=startTs;
            if (i>0) {
                prevTs = data.get(i - 1).ts;
            }
            if (i == data.size() - 1) {
                lastTs=cur.ts;
            }
            long cost=cur.ts - prevTs;

            System.out.println(cur.desc + " cost:" + cost+" ms");
        }
        System.out.println("total cost:" + (lastTs - startTs)+" ms");
        System.out.println("-----------------------");
    }


    class TimeCol{
        long ts;
        String desc;
    }
}
