package com.bc.http;

import com.bc.common.properties.SysProps;
import com.bc.http.modules.DaggerServerComponent;
import com.bc.http.modules.ServerComponent;
import com.bc.http.resource.NetResource;
import com.google.common.base.Charsets;
import org.apache.log4j.Logger;
import org.apache.log4j.lf5.util.StreamUtils;
import org.glassfish.grizzly.http.server.HttpHandler;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.Request;
import org.glassfish.grizzly.http.server.Response;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class NMHttpServer {

    private HttpServer httpServer;
    private final static Logger LOG = Logger.getLogger(NMHttpServer.class);
    private final static String HOST = "localhost";
    private final static int HTTP_PORT = 2088;
    private final static String ROOT_PATH = "/netstat";

    @Inject
    public NMHttpServer() { }

    public void start() {
        // If we've already started, note it and move on
        if (httpServer != null) {
            LOG.info("HTTP server already started, ignoring");
            return;
        }
        URI baseURI = getBaseURI();
        httpServer = GrizzlyHttpServerFactory.createHttpServer(baseURI, buildResourceConfig(), false);

        httpServer.getServerConfiguration().addHttpHandler(createIndexHandler(), "/");
        httpServer.getServerConfiguration().addHttpHandler(createWebHandler(), "/web/*");
        try {
            httpServer.start();
        } catch (IOException ex) {
            LOG.error(ex);
        }
        LOG.info(String.format("HTTP server for hm available at %s", baseURI));
    }

    public void stop() {
        httpServer.shutdown();
        LOG.info(String.format("HTTP server for hm stopped"));
    }

    private ResourceConfig buildResourceConfig() {
        try {
            ResourceConfig resourceConfig = new ResourceConfig();

           // resourc.packages("com.bc.http.resource");
            final ServerComponent component = DaggerServerComponent.create();
            PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(ServerComponent.class).getPropertyDescriptors();
            Set<Object> resources = new HashSet<>();
            for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                Object resource = propertyDescriptor.getReadMethod().invoke(component);
                resourceConfig.register(resource);
                resources.add(resource);
            }

            return resourceConfig;
        } catch (Exception ex) {
            LOG.error(ex.getMessage(), ex);
        }
        return null;
    }

    private URI getBaseURI() {
        return UriBuilder.fromPath(ROOT_PATH).scheme("http").host("0.0.0.0").port(HTTP_PORT).build();
    }

    private HttpHandler createWebHandler() {
        return new HttpHandler("webHandler") {
            @Override
            public void service(Request request, Response response) throws Exception {
                String path = request.getRequestURI();
                LOG.info("Retrieving data from path: " + path);
                String data = getResourceData(path);
                response.setContentType(MediaType.TEXT_HTML);
                response.setContentLength(data == null ? 0 : data.length());
                response.getWriter().write(data);
            }
        };
    }

    private HttpHandler createIndexHandler() {
        return new HttpHandler("indexHandler") {
            @Override
            public void service(Request request, Response response) throws Exception {
                String data = getResourceData("/index.html");
                response.setContentType(MediaType.TEXT_HTML);
                response.setContentLength(data == null ? 0 : data.length());
                response.getWriter().write(data);
            }
        };
    }

    private String getResourceData(String path) throws IOException {
        byte[] ret = null;
        InputStream in = getClass().getResourceAsStream(path);
        if (in == null) {
            return null;
        } else {
            ret = StreamUtils.getBytes(in);
            if (ret == null || ret.length == 0) {
                ret = null;
            }
        }
        if (ret == null) return null;
        return new String(ret, Charsets.UTF_8); //non-null parameter. ret can't be null
    }



}
