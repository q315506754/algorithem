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
    private String delimeter = "\r\n";
    private long startTs = System.currentTimeMillis();

    public void setTitle(String title) {
        this.title = title;
    }

    public TimeAnalyser() {
    }

    public TimeAnalyser(String title) {
        this.title = title;
    }

    public void setDelimeter(String delimeter) {
        this.delimeter = delimeter;
    }

    public void push(String desc) {
        TimeCol one = new TimeCol();
        one.ts = System.currentTimeMillis();
        one.desc = desc;
        data.add(one);
    }
    public void pushStringOnly(String desc) {
        TimeCol one = new TimeCol();
        one.ts = -1;
        one.desc = desc;
        data.add(one);
    }

    public String analyse( ) {
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------");
        sb.append(delimeter);
        if (!StringUtils.isEmpty(title)) {
            sb.append(title);
            sb.append(delimeter);
        }

        for (int i = 0; i < data.size(); i++) {
            TimeCol cur = data.get(i);

            sb.append(cur.desc);

            if (cur.ts > 0) {
                long prevTs=startTs;
                int j = i;
                while(j-->0){
                    prevTs = data.get(j).ts;
                    if (prevTs > 0) {
                        break;
                    }
                }

                long cost=cur.ts - prevTs;

                sb.append(" cost:" + cost+" ms");
            }
            sb.append(delimeter);
        }

        long lastTs=startTs;
        int j = data.size();
        while (j-- >0) {
            lastTs = data.get(j).ts;
            if (lastTs > 0) {
                break;
            }
        }

        sb.append("total cost:" + (lastTs - startTs)+" ms");
        sb.append(delimeter);
        sb.append("-----------------------");

        System.out.println(sb.toString());
        return sb.toString();
    }


    class TimeCol{
        long ts;
        String desc;
    }
}
