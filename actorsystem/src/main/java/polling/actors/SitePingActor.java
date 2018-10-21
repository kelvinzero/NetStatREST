package polling.actors;


import akka.actor.AbstractActor;
import akka.actor.AbstractActorWithTimers;
import akka.actor.Cancellable;
import org.apache.log4j.Logger;
import polling.utils.NetPing;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class SitePingActor extends AbstractActor{

    private final static Logger LOG = Logger.getLogger(SitePingActor.class);

    private Cancellable checkTick;

    public SitePingActor(){
        LOG.debug("Created actor SitePingActor");
        System.out.println("Created");
    }

    @Override
    public AbstractActor.Receive createReceive() {
        return receiveBuilder()
                .matchEquals("tickCheck", tc -> updateStatus())
                .build();
    }

    private void updateStatus(){

    }

    private void init(){
        checkTick = getContext().getSystem().scheduler().schedule(
                Duration.create(1, TimeUnit.MILLISECONDS),
                Duration.create(1000, TimeUnit.MILLISECONDS),
                getSelf(),
                "tickCheck",
                getContext().dispatcher(),
                this.getSelf()
        );
    }
}
