package com.bc.http.resource;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.dispatch.Futures;
import akka.pattern.Patterns;
import akka.util.Timeout;
import com.bc.actorsystem.modules.ActorSystemModule;
import com.bc.actorsystem.polling.messages.PingResponseMsg;
import com.bc.actorsystem.polling.messages.RequestResponseHistoryMsg;
import com.bc.actorsystem.polling.utils.TimeoutPair;
import com.bc.common.properties.ActorNames;
import com.bc.common.properties.ActorPaths;
import com.bc.common.properties.SysProps;
import dagger.Module;
import scala.concurrent.Await;
import scala.concurrent.Future;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import akka.util.Timeout;
import scala.concurrent.duration.FiniteDuration;

import java.util.concurrent.TimeUnit;

@Path("/")
@Module
public class NetResource {

    ActorSystem actorSystem;

    @Inject
    public NetResource(ActorSystem actorSystem){
        this.actorSystem = actorSystem;
    }

    @GET
    @Produces("text/html")
    public String getIt() throws Exception{

        Timeout timeout = Timeout.durationToTimeout(new FiniteDuration(20, TimeUnit.SECONDS));
        StringBuilder sb = new StringBuilder();
        Future<Object> future = Patterns.ask(actorSystem.actorSelection(ActorPaths.SITEPINGER_ACT_PATH), RequestResponseHistoryMsg.create(null), timeout);
        PingResponseMsg response = (PingResponseMsg)Await.result(future, timeout.duration());
        int pingCount = response.getResponses().length;
        long pingAvg = 0;
        sb.append("<h2>Server points to 8.8.8.8</h2>\n");
        sb.append("<div><div  style=\"display: inline-block\"><table>");
        sb.append("<tr><th>Ping</th><th>Time</th><th>Average ping</th><tr>");
        for(TimeoutPair pair : response.getResponses()){
            sb.append(String.format("<tr><td>%d</td><td>%s</td><td>%f</td><tr>", pair.getPing(), pair.getTime().toString(), response.getAvg()));
            pingAvg += pair.getPing();
        }
        pingAvg /= pingCount;
        sb.append("</table></div>&nbsp&nbsp&nbsp");
        sb.append("</div>");
        return sb.toString();
    }
}
