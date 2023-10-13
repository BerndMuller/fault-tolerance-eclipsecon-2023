package de.pdbm.mpft;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Retry;

import java.util.logging.Logger;

@Path("/retry")
@ApplicationScoped
public class RetryResource {

    private static final Logger LOGGER = Logger.getLogger(RetryResource.class.getCanonicalName());

    private int errorCount = 0; // counting down

    @GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    @Retry(maxRetries = 3, delay = 500, retryOn = { Exception.class }) // 3 is default
    @Log
    public String toRetry() throws Exception {
        if (errorCount == 0) {
            return "from 'retry()'";
        } else {
            errorCount--;
            LOGGER.info("about to throw retry exception");
            throw new Exception("retry demo exception");
        }

    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/error-count")
    public Response getErrorCount() {
        return Response.ok(errorCount).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/error-count")
    public Response setSleepTime(int errorCount) {
        LOGGER.info("set error count to " + errorCount);
        this.errorCount = errorCount;
        return Response.ok().build();
    }

}
