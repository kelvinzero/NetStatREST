package com.bc.nm.application.modules;

import com.bc.nm.actorsystem.modules.ActorSystemModule;
import com.bc.nm.application.NetApp;
import dagger.Component;

@Component(modules = {ActorSystemModule.class})
public interface NetMonitorComponent {
    NetApp app();
}
