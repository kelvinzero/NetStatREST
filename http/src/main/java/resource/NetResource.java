package resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/")
public class NetResource {

    @GET
    @Produces("text/html")
    public String getIt() {
        return "hello world";
    }

    @GET
    @Path("/test")
    @Produces("text/html")
    public String test() {
        return "test";
    }

    @GET
    @Path("/test2")
    @Produces("text/html")
    public String test2() {
        return "test2";
    }
}
