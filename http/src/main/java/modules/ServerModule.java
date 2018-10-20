package modules;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class ServerModule {

    private final static String HOST = "localhost";
    private final static int PORT = 2088;
    private final static String ROOT_PATH = "/netstat";
    private final static String BASE_URI = String.format("http://%s:%d%s/", HOST, PORT, ROOT_PATH);

    public static HttpServer getServer(){
        ResourceConfig rc = new ResourceConfig().packages("resource");
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }
}
