package fr.xebia.xebay.client;

import fr.xebia.xebay.domain.*;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

public class SyncClient {
    private final Client client;
    private final String target;

    public SyncClient(String hostAndPort) {
        this.target = "http://" + hostAndPort + "/rest";
        this.client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
    }

    public Set<PublicUser> getPublicUsers() {
        return client.target(target)
                .path("/users/publicUsers")
                .request()
                .get(new GenericType<Set<PublicUser>>() {
                });
    }

    public User getUserInfo(String apiKey) {
        return client.target(target)
                .path("/users/info")
                .request()
                .header(AUTHORIZATION, apiKey)
                .get(User.class);
    }

    public BidOffer getCurrentOffer() {
        return client.target(target)
                .path("/bidEngine/current")
                .request()
                .get(BidOffer.class);
    }

    public BidOffer bid(String name, double newValue, String apiKey) throws BidException {
        BidDemand bidDemand = new BidDemand(name, newValue);
        try {
            return client.target(this.target)
                    .path("/bidEngine").path("/bid").request()
                    .header(AUTHORIZATION, apiKey)
                    .post(Entity.<Object>entity(bidDemand, MediaType.APPLICATION_JSON_TYPE), BidOffer.class);
        } catch (WebApplicationException e) {
            throw bidException(e);
        }
    }

    public void sell(String itemName, double itemValue, String apiKey) {
        client.target(this.target)
                .path("/bidEngine").path("/offer").request()
                .header(AUTHORIZATION, apiKey)
                .post(Entity.<Object>entity(new BidDemand(itemName, itemValue), MediaType.APPLICATION_JSON_TYPE));
    }

    private BidException bidException(WebApplicationException e) {
        StringBuilder errorMessage = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader((InputStream) e.getResponse().getEntity()))) {
            String currentLine;
            while ((currentLine = in.readLine()) != null) {
                if (errorMessage.length() > 0) {
                    errorMessage.append('\n');
                }
                errorMessage.append(currentLine);
            }
        } catch (IOException e1) {
            // ignore
        }
        return new BidException(errorMessage.toString());
    }
}
