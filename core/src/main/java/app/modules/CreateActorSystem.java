package app.modules;

import akka.actor.ActorSystem;
import akka.actor.Props;
import polling.actors.SitePingActor;

import static properties.ActorNames.SITEPINGER_ACT;
import static properties.ActorPaths.SYSTEM_NAME;

public class CreateActorSystem {

    public static ActorSystem actorSystem(){
        ActorSystem system = ActorSystem.create(SYSTEM_NAME);
        system.actorOf(Props.create(SitePingActor.class), SITEPINGER_ACT);
        return system;
    }


}
