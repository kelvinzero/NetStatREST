package actorsystem.polling.actors;


import actorsystem.polling.messages.HistoryResponseMsg;
import actorsystem.polling.messages.RequestResponseHistoryMsg;
import actorsystem.polling.utils.PingPair;
import akka.actor.AbstractActor;
import akka.actor.Cancellable;
import akka.actor.Props;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.apache.log4j.Logger;
import scala.concurrent.duration.Duration;

import java.io.IOException;
import java.net.InetAddress;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

import static com.bc.nm.properties.SysProps.*;

public class SitePollingActor extends AbstractActor{

    private static final Logger LOG = Logger.getLogger(SitePollingActor.class);
    private static final String POLL_SERVER = "POLL_SERVER";
    private CircularFifoQueue<PingPair> responses;
    private CircularFifoQueue<PingPair> timeouts;
    private Cancellable checkScheduler;


    public SitePollingActor(){
        responses = new CircularFifoQueue<>(HISTORY_SIZE);
        timeouts = new CircularFifoQueue<>(HISTORY_SIZE );
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
                Duration.create(POLLING_DELAY, TimeUnit.MILLISECONDS),
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
            InetAddress pingAddress = InetAddress.getByName(SERVER_IP);
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
