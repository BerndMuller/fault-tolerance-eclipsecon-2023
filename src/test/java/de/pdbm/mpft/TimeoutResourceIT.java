package de.pdbm.mpft;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TimeoutResourceIT {

    @Test
    public void getAndSetSleepTime() {
        final int sleepTime = 1234;
        Client client = ClientBuilder.newClient();
		WebTarget target = client.target(TestConfig.SERVER + "/timeout/sleep-time");
        Response postResponse = target.request(MediaType.APPLICATION_JSON).post(Entity.json(sleepTime), Response.class);
        Response getResponse = target.request(MediaType.APPLICATION_JSON).get(Response.class);
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), postResponse.getStatus());
        Assertions.assertEquals(Response.Status.OK.getStatusCode(), getResponse.getStatus());
        Assertions.assertEquals(sleepTime, getResponse.readEntity(Integer.class));
    }
}
