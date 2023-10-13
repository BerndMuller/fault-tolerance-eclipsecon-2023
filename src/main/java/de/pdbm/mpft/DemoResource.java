package de.pdbm.mpft;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/demo")
@ApplicationScoped
public class DemoResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String demo() {
        return """
                http http://localhost:8080/fault-tolerance/timeout/search-demo
                
                http http://localhost:8080/fault-tolerance/timeout/timeout-with-fallback
                http http://localhost:8080/fault-tolerance/timeout/sleep-time
                echo 2000 | http POST http://localhost:8080/fault-tolerance/timeout/sleep-time
                
                http http://localhost:8080/fault-tolerance/retry
                echo 3 | http POST http://localhost:8080/fault-tolerance/retry/error-count
                http http://localhost:8080/fault-tolerance/retry
                echo 6 | http POST http://localhost:8080/fault-tolerance/retry/error-count
                http http://localhost:8080/fault-tolerance/retry
                
                http http://localhost:8080/fault-tolerance/bulkhead/with-thread-pool-isolation
                ab -v 4 -n 10 -c 10 http://localhost:8080/fault-tolerance/bulkhead/with-thread-pool-isolation
                http http://localhost:8080/fault-tolerance/bulkhead/with-semaphore-isolation
                ab -v 4 -n 10 -c 10  http://localhost:8080/fault-tolerance/bulkhead/with-semaphore-isolation
                
                http http://localhost:8080/fault-tolerance/circuit-breaker/fail-fast

                """;
    }
}
