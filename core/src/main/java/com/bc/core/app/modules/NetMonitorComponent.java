package com.bc.core.app.modules;

import com.bc.actorsystem.modules.ActorSystemModule;
import com.bc.core.app.NetApp;
import com.bc.http.NMHttpServer;
import dagger.Component;

@Component (modules = {ActorSystemModule.class})
public interface NetMonitorComponent {
    NetApp app();
}
