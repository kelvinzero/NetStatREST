package com.bc.actorsystem.polling.actors;


import akka.actor.AbstractActor;
import akka.actor.Cancellable;
import com.bc.actorsystem.polling.messages.PingResponseMsg;
import com.bc.actorsystem.polling.messages.RequestResponseHistoryMsg;
import com.bc.actorsystem.polling.utils.TimeoutPair;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.log4j.Logger;
import scala.concurrent.duration.Duration;

import java.io.IOException;
import java.net.InetAddress;
import java.time.Instant;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.bc.common.properties.SysProps.HISTORY_SIZE;
import static com.bc.common.properties.SysProps.POLLING_DELAY;
import static com.bc.common.properties.SysProps.SERVER_IP;

public class SitePollingActor extends AbstractActor{

    private final static Logger LOG = Logger.getLogger(SitePollingActor.class);
    private final static String POLL_SERVER = "POLL_SERVER";
    private CircularFifoQueue<TimeoutPair> responses;
    private CircularFifoQueue<TimeoutPair> timeouts;
    private Cancellable checkScheduler;

    {
        checkScheduler = getContext().getSystem().scheduler().schedule(
                Duration.create(0, TimeUnit.MILLISECONDS),
                Duration.create(POLLING_DELAY, TimeUnit.MILLISECONDS),
                getSelf(),
                POLL_SERVER,
                getContext().dispatcher(),
                this.getSelf()
        );
    }

    public SitePollingActor(){
        responses = new CircularFifoQueue<>(HISTORY_SIZE);
        timeouts = new CircularFifoQueue<>(HISTORY_SIZE );
        LOG.info("Started actor SitePollingActor");
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .match(RequestResponseHistoryMsg.class, this::hangleResponseHistoryRequestMsg)
                .matchEquals(POLL_SERVER, tc -> pollServer())
                .build();
    }

    private void hangleResponseHistoryRequestMsg(RequestResponseHistoryMsg msg){
        sender().tell(PingResponseMsg.create(responses, timeouts, context()), self());
    }


    private void pollServer(){
        responses.add(new TimeoutPair(Instant.now(), sendPing()));
    }

    private long sendPing() {
        long startTime = System.currentTimeMillis();
        long endTime;
        try {
            InetAddress pingAddress = InetAddress.getByName(SERVER_IP);
            if(pingAddress.isReachable(5000)){
                endTime = System.currentTimeMillis();
                return endTime-startTime;
            }
        }catch (IOException e){
            timeouts.add(new TimeoutPair(Instant.now(), -1));
            return -1;
        }
        timeouts.add(new TimeoutPair(Instant.now(), -1));
        return -1;
    }
}
