package com.bc.nm.modules;

import com.bc.nm.actorsystem.modules.modules.ActorSystemModule;
import com.bc.nm.resource.NetResource;
import dagger.Component;

@Component(modules = {ActorSystemModule.class})
public interface ServerComponent {

    NetResource getNetResource();
}
