package com.jiangli.junit.spring.data;

/**
 * @author Jiangli
 * @date 2017/12/28 10:28
 */
public class StatisticModel implements DataCollector{
    private long totalTimes=1;
    private long planCost=0;

    private long maxCost= Long.MIN_VALUE;
    private long minCost= Long.MAX_VALUE;
    private long totalCost=1;
    private long startTs = System.currentTimeMillis();
    private long endTs;

    @Override
    public void start() {
        startTs = System.currentTimeMillis();
    }
    @Override
    public void finish() {
        endTs = System.currentTimeMillis();
        totalCost = endTs - startTs;
    }

    @Override
    public void record(long cost) {
        if (cost > maxCost) {
            maxCost = cost;
        }
        if (cost < minCost) {
            minCost = cost;
        }
    }

    @Override
    public void setTotalTimes(long totalTimes) {
        this.totalTimes = totalTimes;
    }

    @Override
    public long getPlanCost() {
        return planCost;
    }

    @Override
    public void setPlanCost(long planCost) {
        this.planCost = planCost;
    }

    @Override
    public long getTotalTimes() {
        return totalTimes;
    }

    @Override
    public long getTotalCost() {
        return totalCost;
    }


    @Override
    public long getMaxCost() {
        return maxCost;
    }

    @Override
    public long getMinCost() {
        return minCost;
    }

    @Override
    public long getStartTs() {
        return startTs;
    }

    @Override
    public long getEndTs() {
        return endTs;
    }
}
