package com.bc.nm.actorsystem.modules.parent;

import akka.actor.AbstractActor;
import akka.actor.Props;

public class NSParentAct extends AbstractActor {

    public static Props props() {
        return Props.create(NSParentAct.class, NSParentAct::new);
    }

    @Override
    public Receive createReceive() {
        return null;
    }


}
