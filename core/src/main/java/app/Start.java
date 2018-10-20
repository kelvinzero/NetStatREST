package app;

import modules.ServerModule;
import org.glassfish.grizzly.http.server.HttpServer;

import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Start {


    public static void main(String[] args) throws IOException {
       HttpServer server = ServerModule.getServer();
    }

    private static void NetApp(HttpServer server) throws IOException{
        server.start();
    }

}