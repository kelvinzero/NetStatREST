package com.bc.actorsystem.modules;


import akka.actor.ActorSystem;

import dagger.Module;
import dagger.Provides;

import static com.bc.common.properties.ActorPaths.SYSTEM_NAME;

@Module()
public class ActorSystemModule {

    private static ActorSystem actorSystem;

    @Provides
    public ActorSystem provideActorSystem(){
        if(actorSystem == null){
            actorSystem = ActorSystem.create(SYSTEM_NAME);
        }
        return actorSystem;
    }
}
