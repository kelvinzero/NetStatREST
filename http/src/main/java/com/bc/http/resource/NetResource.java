package com.bc.http.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class NetResource {

    @GET
    @Produces("text/html")
    public String getIt() {

        StringBuilder sb = new StringBuilder();
        sb.append("<h2>Server pints to 8.8.8.8</h2>\n");

        return "hello world";
    }
}
