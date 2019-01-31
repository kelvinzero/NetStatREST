package com.bc.http.modules;

import com.bc.actorsystem.modules.ActorSystemModule;
import com.bc.http.resource.NetResource;
import dagger.Component;

@Component (modules = {ActorSystemModule.class})
public interface ServerComponent {

    NetResource getNetResource();
}
