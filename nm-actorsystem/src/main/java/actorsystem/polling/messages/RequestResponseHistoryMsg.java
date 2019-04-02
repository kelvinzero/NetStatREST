package actorsystem.polling.messages;

import akka.actor.ActorRef;

import java.io.Serializable;
import java.time.Instant;

public class RequestResponseHistoryMsg implements Serializable {

    private Instant tsStart;
    private ActorRef sender;

    public static RequestResponseHistoryMsg create(ActorRef sender){
        return new RequestResponseHistoryMsg(sender);
    }

    private RequestResponseHistoryMsg(ActorRef sender){
        this.sender = sender;
        tsStart = Instant.now();
    }

    public Instant getStart() {
        return tsStart;
    }

    public ActorRef getSender() {
        return sender;
    }
}
