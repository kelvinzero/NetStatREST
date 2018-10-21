package com.bc.core.app.modules;

import akka.actor.ActorSystem;
import com.bc.actorsystem.modules.ActorSystemModule;
import com.bc.core.app.Start;
import dagger.Component;

import javax.inject.Inject;

@Component (modules = Start.class)
public interface NetMonitorComponent {
    boolean NetApp();
}
