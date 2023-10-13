package de.pdbm.mpft;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Path("/timeout")
@ApplicationScoped
public class TimeoutResource {

    private static final Logger LOGGER = Logger.getLogger(TimeoutResource.class.getCanonicalName());

    private int sleepTimeInMillis = 0;

    @Inject
    TimeoutSearchDemo timeoutSearchDemo;

    @GET
    @Path("/timeout-with-fallback")
    @Produces(MediaType.TEXT_PLAIN)
    //@Fallback(fallbackMethod = "fallbackMethod")
    @Fallback(SimpleFallbackHandler.class)
    @Timeout(value = 1000, unit = ChronoUnit.MILLIS)
    @Log
    public String timeoutWithFallback() {
        sleep(sleepTimeInMillis);
        return "from 'timeoutWithFallback()'";
    }

    public String fallbackMethod() {
        System.out.println("fallbackMethod called");
        return "from 'fallbackMethod()'";
    }

    @GET
    @Path("/search-demo")
    @Produces(MediaType.TEXT_PLAIN)
    @Log
    public String searchWithSearchEngines() throws Exception {
        return timeoutSearchDemo.getSearchResultFromDuckDuckGo();
    }



    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/sleep-time")
    public Response getSleepTime() {
        return Response.ok(sleepTimeInMillis).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/sleep-time")
    public Response setSleepTime(int millis) { // echo <value> | http http://localhost:8080/...
        LOGGER.info("set sleep time to " + millis);
        sleepTimeInMillis = millis;
        return Response.ok().build();
    }

    private void sleep(int millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
