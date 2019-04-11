package com.bc.nm.application;

import akka.actor.ActorSystem;
import com.bc.nm.application.actor.NSMasterAct;
import com.bc.nm.application.modules.DaggerNetMonitorComponent;
import com.bc.nm.server.NMHttpServer;
import org.apache.log4j.Logger;

import javax.inject.Inject;

public class NetApp {

    private static final Logger LOG = Logger.getLogger(NetApp.class);
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
        actorSystem.actorOf(NSMasterAct.props());
        this.httpServer = httpServer;
    }

    private void start() {
        LOG.info("Starting HTTP server");
        httpServer.start();
    }
}
