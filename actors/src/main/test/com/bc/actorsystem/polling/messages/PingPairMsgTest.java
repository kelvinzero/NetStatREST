package com.bc.actorsystem.polling.messages;

import com.bc.actorsystem.polling.utils.PingPair;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.Random;

class PingPairMsgTest {

    static final long MAX_PING = 10000;
    static PingResponseMsg prm;
    static Random rand;
    static Instant pingTime;
    static PingPair[] responses;
    static PingPair[] timeouts;
    static  int responsesSize;
    static int timeoutsSize;
    static CircularFifoQueue<PingPair> responsesQueue;
    static CircularFifoQueue<PingPair> timeoutsQueue;
    static int responsesQueueSize ;
    static int timeoutsQueueSize;

    static void setupMsgQueues(){
        pingTime = Instant.now();
        rand = new Random(System.currentTimeMillis());

        responses = new PingPair[responsesSize];
        timeouts = new PingPair[timeoutsSize];
        responsesQueue = new CircularFifoQueue<>(responsesQueueSize);
        timeoutsQueue = new CircularFifoQueue<>(timeoutsQueueSize);

        for(int i = 0; i < responsesSize; i++){
            responses[i] = PingPair.create(pingTime, Math.abs(10 + rand.nextLong() % MAX_PING));
            responsesQueue.add(PingPair.create(responses[i].getTime(), responses[i].getPing()));
        }

        for(int i = 0; i < timeoutsSize; i++){
            timeouts[i] = PingPair.create(pingTime, Math.abs(10 + rand.nextLong() % MAX_PING));
            timeoutsQueue.add(PingPair.create(timeouts[i].getTime(), timeouts[i].getPing()));
        }
        prm = PingResponseMsg.create(responsesQueue, timeoutsQueue, null);
    }

    @org.junit.jupiter.api.Test
    void getResponses() {
        // circular queue larger than input
        responsesSize = 10;
        timeoutsSize = 10;
        responsesQueueSize = 15;
        timeoutsQueueSize = 15;
        setupMsgQueues();
        Assertions.assertArrayEquals(responses, prm.getResponsesArray());

        // input larger than circular queue
        responsesSize = 15;
        timeoutsSize = 15;
        responsesQueueSize = 10;
        timeoutsQueueSize = 10;
        setupMsgQueues();
        PingPair[] queueResponsesTrimmed = Arrays.copyOfRange(responses, 5, responses.length);
        PingPair[] queueResponses = prm.getResponsesArray();
        Assertions.assertArrayEquals(queueResponses, queueResponsesTrimmed);
    }

    @Test
    void getTimeouts(){
        // circular queue larger than input
        responsesSize = 10;
        timeoutsSize = 10;
        responsesQueueSize = 15;
        timeoutsQueueSize = 15;
        setupMsgQueues();
        Assertions.assertArrayEquals(timeouts, prm.getTimeoutsArray());

        // input larger than circular queue
        responsesSize = 15;
        timeoutsSize = 15;
        responsesQueueSize = 10;
        timeoutsQueueSize = 10;
        setupMsgQueues();
        PingPair[] queueTimeoutsTrimmed = Arrays.copyOfRange(timeouts, 5, responses.length);
        PingPair[] queueTimeouts = prm.getTimeoutsArray();
        Assertions.assertArrayEquals(queueTimeouts, queueTimeoutsTrimmed);
    }

    @Test
    void testEquals(){
        setupMsgQueues();
        PingResponseMsg prm2 = PingResponseMsg.create(responsesQueue, responsesQueue, null);
        Assertions.assertNotEquals(prm, prm2);
        Assertions.assertEquals(prm, prm);
    }

}