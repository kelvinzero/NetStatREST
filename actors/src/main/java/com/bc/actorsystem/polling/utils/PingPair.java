package com.bc.actorsystem.polling.utils;

import javax.annotation.Nonnull;
import java.security.InvalidParameterException;
import java.time.Instant;

public class PingPair {

    private Instant time;
    private long ping;

    public static PingPair create(@Nonnull Instant time, long ping){
        return new PingPair(time, ping);
    }

    private PingPair(Instant time, long ping){
        if(ping < 0){
            throw new InvalidParameterException("ping time less than zero");
        }
        this.time = time;
        this.ping = ping;
    }

    public Instant getTime() {
        return time;
    }

    public long getPing() {
        return ping;
    }

    @Override
    public boolean equals(Object obj) {
        if(!obj.getClass().equals(this.getClass()))
            return false;
        PingPair that = (PingPair) obj;
        return this.ping == that.ping && this.time.equals(that.time);
    }
}
