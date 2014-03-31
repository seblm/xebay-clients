package fr.xebia.xebay.client;

import fr.xebia.xebay.domain.BidOffer;
import fr.xebia.xebay.domain.PublicUser;
import fr.xebia.xebay.domain.User;
import fr.xebia.xebay.domain.BidDemand;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.util.Set;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

public class RestBidder {
    private static final Logger log = LoggerFactory.getLogger(RestBidder.class);

    private final Client client;
    private final String target;

    public RestBidder(String target) {
        this.target = target;
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
        User user = client.target(target)
                .path("/users/info")
                .request()
                .header(AUTHORIZATION, apiKey)
                .get(User.class);
        log.info("User info : " + user.toString());
        return user;
    }

    public BidOffer getCurrentOffer() {
        return client.target(target)
                .path("/bidEngine/current")
                .request()
                .get(BidOffer.class);
    }

    public BidOffer bid(String name, double newValue, String apiKey) {
        BidDemand bidDemand = new BidDemand(name, newValue);

        return client.target(this.target)
                .path("/bidEngine").path("/bid").request()
                .header(AUTHORIZATION, apiKey)
                .post(Entity.<Object>entity(bidDemand, MediaType.APPLICATION_JSON_TYPE), BidOffer.class);
    }

    public void sell(String itemName, double itemValue, String apiKey) {
        log.debug("Selling an item ");
        client.target(this.target)
                .path("/bidEngine").path("/offer").request()
                .header(AUTHORIZATION, apiKey)
                .post(Entity.<Object>entity(new BidDemand(itemName, itemValue), MediaType.APPLICATION_JSON_TYPE));
        log.debug("item " + itemName + " was sent for sale ");
    }
}
