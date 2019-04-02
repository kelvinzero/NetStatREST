package actorsystem.polling.messages;

import akka.actor.ActorContext;
import actorsystem.polling.utils.PingPair;
import org.apache.commons.collections4.queue.CircularFifoQueue;


import java.io.Serializable;
import java.util.Arrays;

public class PingResponseMsg implements Serializable {

    private CircularFifoQueue<PingPair> responsesQueue;
    private CircularFifoQueue<PingPair> timeoutsQueue;
    private ActorContext sender;

    public static PingResponseMsg create(CircularFifoQueue<PingPair> responsesQueue, CircularFifoQueue<PingPair> timeoutsQueue, ActorContext sender){
       return new PingResponseMsg(responsesQueue, timeoutsQueue, sender);
    }

    private PingResponseMsg(CircularFifoQueue<PingPair> responsesQueue, CircularFifoQueue<PingPair> timeoutsQueue, ActorContext sender){
        this.responsesQueue = responsesQueue;
        this.timeoutsQueue = timeoutsQueue;
        this.sender = sender;
    }

    public ActorContext getSender() {
        return sender;
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
        if(!obj.getClass().equals(this.getClass()))
            return false;
        PingResponseMsg that = (PingResponseMsg) obj;
        if(Arrays.equals(that.getResponsesArray(), this.getResponsesArray()) &&
                Arrays.equals(that.getTimeoutsArray(), this.getTimeoutsArray())){
            return true;
        }
        return false;
    }
}
