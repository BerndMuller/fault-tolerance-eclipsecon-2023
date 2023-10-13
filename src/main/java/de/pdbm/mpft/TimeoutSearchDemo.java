package de.pdbm.mpft;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.faulttolerance.Fallback;
import org.eclipse.microprofile.faulttolerance.Timeout;

import java.util.logging.Logger;

public class TimeoutSearchDemo {

    private static final Logger LOGGER = Logger.getLogger(TimeoutSearchDemo.class.getCanonicalName());

    @Timeout(value = 1000)
    @Fallback(fallbackMethod = "getSearchResultFromGoogle")
    public String getSearchResultFromDuckDuckGo() {
        LOGGER.info("getSearchResult() (fromDuckDuckGo)");
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://duckduckgo.com/?q=MicroProfile");
        String result = target.request(MediaType.TEXT_HTML_TYPE).get(String.class);
        return "Got " + result.length() + " bytes from DuckDuckGo";
    }


    public String getSearchResultFromGoogle() {
        LOGGER.info("getSearchResultFromGoogle()");
        Client client = ClientBuilder.newClient();
		WebTarget target = client.target("https://www.google.com/?q=MicroProfile");
        String result = target.request(MediaType.TEXT_HTML_TYPE).get(String.class);
        return "Got " + result.length() + " bytes from Google";
    }

}
