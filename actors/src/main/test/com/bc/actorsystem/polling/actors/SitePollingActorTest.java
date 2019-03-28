package com.bc.actorsystem.polling.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.javadsl.TestKit;
import com.bc.actorsystem.polling.messages.PingResponseMsg;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.bc.common.properties.ActorNames.*;

class SitePollingActorTest {

    static ActorSystem system;

    @BeforeAll
    static void createSystem(){
        system = ActorSystem.create("testSystem");
    }

    @AfterAll
    static void teardownSystem(){
        TestKit.shutdownActorSystem(system);
    }

    @Test
    void testSitePollingActorResponse(){
        final ActorRef sitePollingAct = system.actorOf(Props.create(SitePollingActor.class), SITEPOLLING_ACTOR);
        final TestKit probe = new TestKit(system);
        sitePollingAct.tell(new SitePollingActor.RequestPingResponseMsg(), probe.getRef());
        probe.expectMsgClass(PingResponseMsg.class);
    }

}