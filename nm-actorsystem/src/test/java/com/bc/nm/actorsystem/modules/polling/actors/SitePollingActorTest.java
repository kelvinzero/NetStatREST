package com.bc.nm.actorsystem.modules.polling.actors;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.testkit.javadsl.TestKit;
import com.bc.nm.actorsystem.modules.polling.messages.HistoryResponseMsg;
import com.bc.nm.actorsystem.modules.polling.messages.RequestResponseHistoryMsg;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SitePollingActorTest {

    private static ActorSystem system;

    @BeforeAll
    static void setup() {
        system = ActorSystem.create();
    }

    @AfterAll
    static void teardown() {
        TestKit.shutdownActorSystem(system);
        system = null;
    }

    @Test
    void historyRequestTest() {
        final TestKit probe = new TestKit(system);
        final ActorRef sitePollingAct = system.actorOf(SitePollingActor.props());
        sitePollingAct.tell(RequestResponseHistoryMsg.create(probe.getRef()), probe.getRef());
        probe.expectMsgAnyClassOf(HistoryResponseMsg.class);
    }
}