package com.bc.actorsystem.polling.utils;

import java.time.Instant;

public class TimeoutPair {

    private Instant time;
    private long ping;

    public static TimeoutPair create(Instant time, long ping){
        return new TimeoutPair(time, ping);
    }

    private TimeoutPair(Instant time, long ping){
        this.time = time;
        this.ping = ping;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public long getPing() {
        return ping;
    }

    public void setPing(long ping) {
        this.ping = ping;
    }
}
