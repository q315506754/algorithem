package com.jiangli.concurrent.util;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by Jiangli on 2016/7/7.
 */
public class ScheduleExecutor {
    static ScheduledFuture<?> scheduledFuture = null;
    public static void main(String[] args) {
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();

        scheduledFuture = service.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println(System.currentTimeMillis());
                System.out.println(scheduledFuture.getDelay(TimeUnit.MILLISECONDS));
            }
        }, 0, 500, TimeUnit.MILLISECONDS);
    }
}
