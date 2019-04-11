package com.bc.nm.internetmon.actors;


import akka.actor.AbstractActor;
import akka.actor.Cancellable;
import akka.actor.Props;
import com.bc.nm.actorsystem.actorbase.NSParentAct;
import com.bc.nm.internetmon.messages.HistoryResponseMsg;
import com.bc.nm.internetmon.messages.RequestResponseHistoryMsg;
import com.bc.nm.internetmon.utils.PingPair;
import com.bc.nm.properties.SysProps;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.log4j.Logger;
import scala.concurrent.duration.Duration;

import java.io.IOException;
import java.net.InetAddress;
import java.time.Instant;
import java.util.concurrent.TimeUnit;


public class SitePollingActor extends NSParentAct {

    private static final Logger LOG = Logger.getLogger(SitePollingActor.class);
    private static final String POLL_SERVER = "POLL_SERVER";
    private CircularFifoQueue<PingPair> responses;
    private CircularFifoQueue<PingPair> timeouts;
    private Cancellable checkScheduler;


    private SitePollingActor() {
        responses = new CircularFifoQueue<>(SysProps.HISTORY_SIZE);
        timeouts = new CircularFifoQueue<>(SysProps.HISTORY_SIZE);
        LOG.info("Started actor SitePollingActor");
    }

    public static Props props() {
        return Props.create(SitePollingActor.class, SitePollingActor::new);
    }

    @Override
    public void preStart() throws Exception {
        startScheduler();
    }

    private void startScheduler() {
        checkScheduler = getContext().getSystem().scheduler().schedule(
                Duration.create(0, TimeUnit.MILLISECONDS),
                Duration.create(SysProps.POLLING_DELAY, TimeUnit.MILLISECONDS),
                getSelf(),
                POLL_SERVER,
                getContext().dispatcher(),
                this.getSelf()
        );
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(RequestResponseHistoryMsg.class, msg -> handleResponseHistoryRequestMsg())
                .matchEquals(POLL_SERVER, tc -> pollServer())
                .build();
    }

    private void handleResponseHistoryRequestMsg() {
        sender().tell(HistoryResponseMsg.create(responses, timeouts), self());
    }

    @Override
    public void postStop() {
        checkScheduler.cancel();
    }

    private void pollServer(){
        PingPair pair = PingPair.create(Instant.now(), sendPing());
        if(pair.getPing() != -1)
            responses.add(pair);
    }

    private long sendPing() {
        long startTime = System.currentTimeMillis();
        long endTime;
        try {
            InetAddress pingAddress = InetAddress.getByName(SysProps.SERVER_IP);
            if(pingAddress.isReachable(5000)){
                endTime = System.currentTimeMillis();
                return endTime-startTime;
            }
        }catch (IOException e){
            timeouts.add(PingPair.create(Instant.now(), -1));
            return -1;
        }
        timeouts.add(PingPair.create(Instant.now(), -1));
        return -1;
    }
}
