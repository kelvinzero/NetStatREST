package com.bc.nm.application.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.bc.nm.actorsystem.actorbase.NSParentAct;
import com.bc.nm.internetmon.actors.SitePollingActor;

import static com.bc.nm.properties.ActorNames.SITE_POLLING_ACTOR;

public class NSMasterAct extends NSParentAct {

    public static Props props() {
        return Props.create(NSMasterAct.class, NSMasterAct::new);
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(InitializeNMBaseActMsg.class, initializeNMBaseAct -> {
                })
                .build();
    }

    private void initializeBaseAct() {
        context().actorOf(Props.create(SitePollingActor.class), SITE_POLLING_ACTOR);
    }

    private NSMasterAct() {
    }

    public static class InitializeNMBaseActMsg {
    }
}
