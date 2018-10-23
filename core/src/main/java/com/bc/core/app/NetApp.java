package com.bc.core.app;

import akka.actor.ActorSystem;
import akka.actor.Props;
import com.bc.actorsystem.polling.actors.SitePollingActor;
import com.bc.core.app.modules.DaggerNetMonitorComponent;
import com.bc.http.modules.ServerModule;
import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;

import javax.inject.Inject;
import java.io.IOException;

import static com.bc.common.properties.ActorNames.SITEPOLLING_ACTOR;

public class NetApp {

    private final static Logger LOG = Logger.getLogger(NetApp.class);

    public static void main(String[] args) {
        DaggerNetMonitorComponent.create().app().start();
    }

    @Inject
    public NetApp(ActorSystem actorSystem){
        LOG.info("Starting NetMonitor app");
        LOG.info("Starting actors");
        actorSystem.actorOf(Props.create(SitePollingActor.class), SITEPOLLING_ACTOR);

    }

    public void start(){
        LOG.info("Starting HTTP server");
        HttpServer server = ServerModule.getServer();
        try {
            server.start();
        }catch (IOException e){
            LOG.error("Couldn't start HTTP server");
        }
    }

}
