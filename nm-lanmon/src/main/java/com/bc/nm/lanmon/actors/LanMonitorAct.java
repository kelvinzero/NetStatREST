package com.bc.nm.lanmon.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.bc.nm.actorsystem.actorbase.NSParentAct;

public class LanMonitorAct extends NSParentAct {

    public static Props props() {
        return Props.create(LanMonitorAct.class, LanMonitorAct::new);
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .build();
    }

}
