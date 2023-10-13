package de.pdbm.mpft;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.faulttolerance.CircuitBreaker;
import org.eclipse.microprofile.faulttolerance.exceptions.CircuitBreakerOpenException;

import java.time.temporal.ChronoUnit;
import java.util.Random;
import java.util.logging.Logger;

@Path("/circuit-breaker")
@ApplicationScoped
public class CircuitBreakerResource {

    private static final Logger LOGGER = Logger.getLogger(TimeoutResource.class.getCanonicalName());

    @Inject
    CircuitBreakerService circuitBreakerService;

    @GET
    @Path("/fail-fast")
    public String failFast() throws Exception {
        try {
            return circuitBreakerService.failFast();
        } catch (CircuitBreakerOpenException e) {
            LOGGER.info("CircuitBreaker is open");
            throw e;
        }
    }

}

class CircuitBreakerService {

    //@CircuitBreaker(failOn = {Throwable.class}, skipOn = {}, delay = 5000L, delayUnit = ChronoUnit.MILLIS, requestVolumeThreshold = 20, failureRatio = 0.5, successThreshold = 1) // Defaults
    @CircuitBreaker(failOn = {Throwable.class}, skipOn = {}, delay = 5000L, delayUnit = ChronoUnit.MILLIS, requestVolumeThreshold = 5, failureRatio = 0.5, successThreshold = 1)
    public String failFast() throws Exception {
        if (new Random().nextDouble() < 0.7) {
            throw new Exception("something went wrong");
        }
        return "from CircuitBreakerService#failFast()";

    }
}
