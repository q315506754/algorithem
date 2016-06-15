package com.jiangli.jdk.v1_8;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/15 0015 16:55
 */
public class ClockTest {
    public static void main(String[] args) {
        Clock clock = Clock.systemDefaultZone();
        long millis = clock.millis();
        System.out.println("clock:"+millis);
        System.out.println("ts:"+System.currentTimeMillis());


        Instant instant = clock.instant();
        Date legacyDate = Date.from(instant);   // legacy java.util.Date

    }

}
