package com.jiangli.concurrent.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jiangli on 2016/7/7.
 */
public class ScheduleExecutor2 {
    static ScheduledFuture<?> scheduledFuture = null;
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        scheduledFuture = service.schedule(new Runnable() {
            @Override
            public void run() {
                System.out.println("running...");
            }
        }, 2000, TimeUnit.MILLISECONDS);

        while (!scheduledFuture.isDone()) {
            long delay = scheduledFuture.getDelay(TimeUnit.MILLISECONDS);
            System.out.println(delay);
        }
    }
}
