package com.bc.nm.internetmon.messages;

import com.bc.nm.internetmon.utils.PingPair;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.io.Serializable;
import java.util.Arrays;

public class HistoryResponseMsg implements Serializable {

    private CircularFifoQueue<PingPair> responsesQueue;
    private CircularFifoQueue<PingPair> timeoutsQueue;

    public static HistoryResponseMsg create(CircularFifoQueue<PingPair> responsesQueue, CircularFifoQueue<PingPair> timeoutsQueue) {
        return new HistoryResponseMsg(responsesQueue, timeoutsQueue);
    }

    private HistoryResponseMsg(CircularFifoQueue<PingPair> responsesQueue, CircularFifoQueue<PingPair> timeoutsQueue) {
        this.responsesQueue = responsesQueue;
        this.timeoutsQueue = timeoutsQueue;
    }

    public PingPair[] getResponsesArray() {
        return responsesQueue.toArray(new PingPair[responsesQueue.size()]);
    }

    public PingPair[] getTimeoutsArray() {
        return timeoutsQueue.toArray(new PingPair[timeoutsQueue.size()]);
    }

    public CircularFifoQueue<PingPair> getResponsesQueue(){
        return this.responsesQueue;
    }

    public CircularFifoQueue<PingPair> getTimeoutsQueue(){
        return this.timeoutsQueue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if(!obj.getClass().equals(this.getClass()))
            return false;
        HistoryResponseMsg that = (HistoryResponseMsg) obj;
        return Arrays.equals(that.getResponsesArray(), this.getResponsesArray()) &&
                Arrays.equals(that.getTimeoutsArray(), this.getTimeoutsArray());
    }
}
