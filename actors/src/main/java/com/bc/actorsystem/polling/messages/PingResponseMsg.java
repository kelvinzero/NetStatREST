package com.bc.actorsystem.polling.messages;

import akka.actor.ActorContext;
import com.bc.actorsystem.polling.utils.TimeoutPair;
import org.apache.commons.collections4.queue.CircularFifoQueue;

import java.io.Serializable;
import java.util.ArrayList;

public class PingResponseMsg implements Serializable {

    private CircularFifoQueue<TimeoutPair> responses;
    private CircularFifoQueue<TimeoutPair> timeouts;
    private ActorContext sender;

    public static PingResponseMsg create(CircularFifoQueue<TimeoutPair> responses, CircularFifoQueue<TimeoutPair> timeouts, ActorContext sender){
       return new PingResponseMsg(responses, timeouts, sender);
    }
    

    private PingResponseMsg(CircularFifoQueue<TimeoutPair> responses, CircularFifoQueue<TimeoutPair> timeouts, ActorContext sender){
        this.responses = responses;
        this.timeouts = timeouts;
        this.sender = sender;
    }

    public TimeoutPair[] getResponses() {
        return responses.toArray(new TimeoutPair[responses.size()]);
    }

    public ActorContext getSender() {
        return sender;
    }

    public void setSender(ActorContext sender) {
        this.sender = sender;
    }

    public TimeoutPair[] getTimeouts() {
        return (TimeoutPair[])timeouts.toArray();
    }

    public float getAvg(){
        float total = 0.0f;
        for(TimeoutPair timeout : responses){
            total += timeout.getPing();
        }
        return total / responses.size();
    }
}
