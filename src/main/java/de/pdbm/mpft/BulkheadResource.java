package de.pdbm.mpft;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.container.AsyncResponse;
import jakarta.ws.rs.container.Suspended;
import org.eclipse.microprofile.faulttolerance.Asynchronous;
import org.eclipse.microprofile.faulttolerance.Bulkhead;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Path("/bulkhead")
@ApplicationScoped
public class BulkheadResource {

    private static final Logger LOGGER = Logger.getLogger(BulkheadResource.class.getCanonicalName());
    public static final int BUSY = 5;

    @GET
    @Path("/with-thread-pool-isolation")
    @Asynchronous // enforce thread pool isolation
    @Bulkhead(value = 3, waitingTaskQueue = 5)
    public CompletionStage<String> bulkheadThreadPoolIsolation() {
        return bulkhead();
    }

    @GET
    @Path("/with-semaphore-isolation")
    @Bulkhead(value = 3)
    public CompletionStage<String> bulkheadSemaphoreIsolation() {
        return bulkhead();
    }

    private CompletionStage<String> bulkhead() {
        String id = Thread.currentThread().getName();
        LOGGER.info(id + " started");
        for (int i = 0; i < BUSY; i++) {
            sleep(100);
            LOGGER.info(id + " working");
        }
        LOGGER.info(id + " about to end");
        return CompletableFuture.completedFuture("returned from " + id);
    }


    private void sleep(int millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
