package com.jiangli.junit.spring.data;

/**
 * @author Jiangli
 * @date 2017/12/28 10:37
 */
public interface DataCollector {
    void start();

    void finish();

    void record(long cost);

    long getPlanCost();

    void setPlanCost(long planCost);

    long getTotalTimes();

    void setTotalTimes(long totalTimes);

    long getTotalCost();

    long getMaxCost();

    long getMinCost();

    long getStartTs();

    long getEndTs();
}
