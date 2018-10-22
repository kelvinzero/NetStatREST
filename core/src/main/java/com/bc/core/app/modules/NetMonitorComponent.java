package com.bc.core.app.modules;

import com.bc.actorsystem.modules.ActorSystemModule;
import com.bc.core.app.NetApp;
import dagger.Component;

@Component (modules = ActorSystemModule.class)
public interface NetMonitorComponent {
    NetApp app();
}
