package com.jiangli.junit.spring;

import com.jiangli.junit.spring.group.invoker.GroupInvoker;

/**
 * @author Jiangli
 * @date 2017/12/28 14:13
 */
public class InvokeContext {
    private RepeatFixedTimes fixedTimes ;
    private RepeatFixedDuration fixedDuration;
    private GroupInvoker  groupInvoker;
    private boolean  useDefault = true;
    private InvokeMode  invokeMode  ;
    private int  threadNum =1 ;//default
    private boolean printDetail = false; //default

    private boolean multiThread ; //

    public InvokeContext init() {
        if (fixedTimes!=null) {
            useDefault = false;
            invokeMode = InvokeMode.FIXED_TIMES;
            printDetail = fixedTimes.printDetail();
            threadNum = fixedTimes.thread();
        }
        if (fixedDuration!=null) {
            useDefault = false;
            invokeMode = InvokeMode.FIXED_DURATION;
            printDetail = fixedDuration.printDetail();
            threadNum = fixedDuration.thread();
        }

        if (useDefault) {
            invokeMode = InvokeMode.DEFAULT;
        }
        multiThread =  threadNum > 1;
        return this;
    }


    public boolean isMultiThread() {
        return multiThread;
    }

    public InvokeContext setMultiThread(boolean multiThread) {
        this.multiThread = multiThread;
        return this;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public InvokeContext setThreadNum(int threadNum) {
        this.threadNum = threadNum;
        return this;
    }

    public boolean isPrintDetail() {
        return printDetail;
    }

    public InvokeContext setPrintDetail(boolean printDetail) {
        this.printDetail = printDetail;
        return this;
    }

    public InvokeMode getInvokeMode() {
        return invokeMode;
    }

    public InvokeContext setInvokeMode(InvokeMode invokeMode) {
        this.invokeMode = invokeMode;
        return this;
    }

    public boolean isUseDefault() {
        return useDefault;
    }

    public InvokeContext setUseDefault(boolean useDefault) {
        this.useDefault = useDefault;
        return this;
    }

    public InvokeContext(GroupInvoker groupInvoker) {
        this.groupInvoker = groupInvoker;
    }


    public GroupInvoker getGroupInvoker() {
        return groupInvoker;
    }

    public InvokeContext setGroupInvoker(GroupInvoker groupInvoker) {
        this.groupInvoker = groupInvoker;
        return this;
    }

    public RepeatFixedTimes getFixedTimes() {
        return fixedTimes;
    }

    public InvokeContext setFixedTimes(RepeatFixedTimes fixedTimes) {
        this.fixedTimes = fixedTimes;
        return this;
    }

    public RepeatFixedDuration getFixedDuration() {
        return fixedDuration;
    }

    public InvokeContext setFixedDuration(RepeatFixedDuration fixedDuration) {
        this.fixedDuration = fixedDuration;
        return this;
    }
}
