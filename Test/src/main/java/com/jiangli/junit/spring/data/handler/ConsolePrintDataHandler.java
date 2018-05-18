package com.jiangli.junit.spring.data.handler;


import com.jiangli.junit.spring.InvokeContext;
import com.jiangli.junit.spring.data.DataCollector;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @author Jiangli
 * @date 2017/12/28 11:18
 */
public class ConsolePrintDataHandler implements DataHandler {
    public String format(double num) {
        return format((long)num);
    }
    public String format(long num) {
        NumberFormat numberFormat = new DecimalFormat(",###");
        return numberFormat.format(num);
    }

    @Override
    public void handler(InvokeContext context, DataCollector model) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>" + context.getGroupInvoker().getGroupName() + ">>>>>>>>>>>>>>>>>>>>");
        System.out.println("\t>>>>>>>>模式:(" + context.getInvokeMode() + ")>>>>>>>>>>>>>>>");
        if (context.isMultiThread()) {
            System.out.println("\t>>>>>>>> 线程数 * " + context.getThreadNum() + " >>>>>>>>>>>>>>>>>>>>>>>");
        }
        System.out.println("\t总次数:" + format(model.getTotalTimes()) + "");
        System.out.println("\t总耗时:" + model.getTotalCost() + "ms");
        if (!context.isMultiThread()) {
            System.out.println("\t\t平均:" + (model.getTotalCost()) / model.getTotalTimes() + "ms");
            System.out.println("\t\t最高:" + model.getMaxCost() + "ms");
            System.out.println("\t\t最低:" + model.getMinCost() + "ms");
        }
        if (model.getTotalCost() > 0) {
            System.out.println("\t\ttps:" + format(model.getTotalTimes() * 1000.0 / model.getTotalCost()) + " /s");
        } else {
            System.out.println("\t\ttps 总耗时过小 无法估算");
        }
        if (model.getTotalTimes() > 2 && model.getTotalCost() > 0) {
            long reducedTimes = model.getTotalTimes() - 2;
            long reducedTotalCost = model.getTotalCost() - model.getMaxCost() - model.getMinCost();
            System.out.println("\t\ttps(去掉最高和最低):" + format(reducedTimes * 1000.0 / reducedTotalCost) + " /s");
        }
        System.out.println("------------------------------------------------");
    }
}
