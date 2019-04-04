package com.bc.nm.application;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.bc.nm.actorsystem.modules.polling.actors.SitePollingActor;
import com.bc.nm.application.modules.DaggerNetMonitorComponent;
import com.bc.nm.server.NMHttpServer;
import org.apache.log4j.Logger;

import javax.inject.Inject;

import static com.bc.nm.properties.ActorNames.SITE_POLLING_ACTOR;

public class NetApp {

    private final static Logger LOG = Logger.getLogger(NetApp.class);
    private NMHttpServer httpServer;

    public static void main(String[] args) {
        DaggerNetMonitorComponent.create().app().start();
    }

    @Inject
    public NetApp(
            ActorSystem actorSystem,
            NMHttpServer httpServer) {
        LOG.info("Starting NetMonitor app");
        LOG.info("Starting actors");
        actorSystem.actorOf(Props.create(SitePollingActor.class), SITE_POLLING_ACTOR);
        LOG.info("Starting HTTP server");
        this.httpServer = httpServer;
    }

    private void start() {
        LOG.info("Starting HTTP server");
        httpServer.start();
    }
}
