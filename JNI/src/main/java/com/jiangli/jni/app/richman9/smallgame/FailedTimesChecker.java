package com.jiangli.jni.app.richman9.smallgame;

/**
 * Created by Jiangli on 2016/6/16.
 */
public class FailedTimesChecker {
    private final int failedThreshold;
    private  int failedTimes = 0;
    private  Runnable  whenReached;

    public FailedTimesChecker(int failedThreshold) {
        this.failedThreshold = failedThreshold;
    }

    public void setWhenReached(Runnable whenReached) {
        this.whenReached = whenReached;
    }

    public void incFailedTime(boolean condition){
        if (condition) {
            failedTimes ++;
        }else{
            resetFailed();
        }

        if (failedTimes > failedThreshold) {
            resetFailed();

            if (whenReached !=null) {
                whenReached.run();
            }
        }
    }

    private void resetFailed() {
        failedTimes = 0;
    }
}
