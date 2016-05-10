package com.jiangli.memory;

import java.util.Comparator;

public class MaxComparator implements Comparator<MemoryAnalyseResult> {

    @Override
    public int compare(MemoryAnalyseResult o1, MemoryAnalyseResult o2) {
        return o1.getMaximumCostTime() > o2.getMaximumCostTime() ? -1 : 1;
    }


}
