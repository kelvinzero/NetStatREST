package com.bc.actorsystem.polling.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;
import java.time.Instant;

class PingPairTest {

    static PingPair pingPair;
    static Instant time;

    @BeforeAll
    static void createTimeoutPair(){
        time = Instant.now();
        pingPair = PingPair.create(time, 30);
    }

    @Test
    void invalidParameterException(){
        Assertions.assertThrows(InvalidParameterException.class, () -> {
            PingPair.create(time, -1);
        });
    }

    @Test
    void getPing(){
        Assertions.assertEquals(30, pingPair.getPing());
    }

    @Test
    void getTime(){
        Assertions.assertEquals(time, pingPair.getTime());
    }

    @Test
    void testEquals(){
        Instant now = Instant.now();
        PingPair pairOne = PingPair.create(now, 155);
        PingPair pairTwo = PingPair.create(now, 155);
        Assertions.assertEquals(pairOne, pairTwo);
    }
}