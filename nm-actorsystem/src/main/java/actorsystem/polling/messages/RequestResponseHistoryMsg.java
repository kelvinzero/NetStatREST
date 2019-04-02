package actorsystem.polling.messages;

import akka.actor.ActorRef;

import java.io.Serializable;
import java.time.Instant;

public class RequestResponseHistoryMsg implements Serializable {

    private Instant ts_start;
    private ActorRef sender;

    public static RequestResponseHistoryMsg create(ActorRef sender){
        return new RequestResponseHistoryMsg(sender);
    }

    private RequestResponseHistoryMsg(ActorRef sender){
        this.sender = sender;
        ts_start = Instant.now();
    }

    public Instant getStart() {
        return ts_start;
    }

    public ActorRef getSender() {
        return sender;
    }
}
