package com.bc.nm.actorsystem.modules;

import akka.actor.ActorSystem;
import dagger.Module;
import dagger.Provides;

import static com.bc.nm.properties.ActorPaths.SYSTEM_NAME;

@Module()
public class ActorSystemModule {

    private ActorSystemModule() {
    }

    private static ActorSystem actorSystem;

    @Provides
    public static ActorSystem provideActorSystem() {
        if(actorSystem == null){
            actorSystem = ActorSystem.create(SYSTEM_NAME);
        }
        return actorSystem;
    }
}
