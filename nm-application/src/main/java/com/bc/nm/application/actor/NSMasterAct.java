package com.bc.nm.application.actor;

import akka.actor.AbstractActor;
import akka.actor.Props;
import com.bc.nm.actorsystem.actorbase.NSParentAct;
import com.bc.nm.internetmon.actors.SitePollingActor;
import org.apache.log4j.Logger;

import static com.bc.nm.properties.ActorNames.SITE_POLLING_ACTOR;

public class NSMasterAct extends NSParentAct {

    private static final Logger LOG = Logger.getLogger(NSMasterAct.class);

    private static class InitializeNMBaseActMsg {
    }

    public static Props props() {
        return Props.create(NSMasterAct.class, NSMasterAct::new);
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(InitializeNMBaseActMsg.class, msg -> initializeBaseAct())
                .build();
    }

    private void initializeBaseAct() {
        context().actorOf(Props.create(SitePollingActor.class), SITE_POLLING_ACTOR);
    }

    private NSMasterAct() {
        LOG.info("Starting NSMasterAct");
        self().tell(new InitializeNMBaseActMsg(), self());
    }
}
