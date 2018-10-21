package com.bc.core.app;

import akka.actor.ActorSystem;
import com.bc.core.app.modules.DaggerNetMonitorComponent;
import com.bc.core.app.modules.NetMonitorComponent;
import com.bc.http.modules.ServerModule;
import dagger.Module;
import dagger.Provides;
import org.glassfish.grizzly.http.server.HttpServer;

import javax.inject.Inject;
import java.io.IOException;

@Module
public class Start implements NetMonitorComponent {

    public static void main(String[] args) throws IOException {
        DaggerNetMonitorComponent.create().NetApp();
    }

    @Provides
    public boolean NetApp(){
        System.out.println("Starting NetMonitor app");
        System.out.println("Starting actors");
        System.out.println("Starting HTTP server");
        HttpServer server = ServerModule.getServer();
        try {
            server.start();
        }catch (IOException e){
            System.out.println("Couldn't start HTTP server");
        }
        return true;
    }

}
