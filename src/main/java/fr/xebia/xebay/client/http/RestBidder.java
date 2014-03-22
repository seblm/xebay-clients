package fr.xebia.xebay.client.http;

import fr.xebia.xebay.domain.BidOffer;
import fr.xebia.xebay.domain.Item;
import fr.xebia.xebay.domain.PublicUser;
import fr.xebia.xebay.domain.User;
import fr.xebia.xebay.dto.BidDemand;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Set;

import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;

public class RestBidder {
    private static final String ADMIN_KEY = "4dm1n";
    private static final Logger log = LoggerFactory.getLogger("RestBidder");

    private final WebTarget webTarget;
    private final Client client;
    private final String target;

    public RestBidder(String target) {
        this.target = target;
        this.client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
        this.webTarget = client.target(this.target).path("/bidEngine");
    }

    public String register(String name) {
        return client
                .target(target)
                .path("/users/register")
                .queryParam("name", name)
                .request()
                .header(AUTHORIZATION, ADMIN_KEY)
                .get(String.class);
    }

    public void unregister(String apiKey) {
        client
                .target(target)
                .path("/users/unregister")
                .queryParam("key", apiKey)
                .request()
                .header(AUTHORIZATION, ADMIN_KEY)
                .delete();
    }

    public Set<PublicUser> getPublicUsers() {
        return client.target(target)
                .path("/users/publicUsers")
                .request()
                .get(new GenericType<Set<PublicUser>>() {
                });
    }

    public Set<User> getUsers() {
        return client.target(target)
                .path("/users")
                .request()
                .header(AUTHORIZATION, ADMIN_KEY)
                .get(new GenericType<Set<User>>() {
                });
    }

    public User getUserInfo(String apiKey) {
        User user = client.target(target).path("/users/info")
                .request()
                .header(AUTHORIZATION, apiKey)
                .get(User.class);
        log.info("User info : " + user.toString());
        return user;
    }

    public BidOffer getCurrentOffer() {
        return webTarget.path("/current").request().get(BidOffer.class);
    }

    public BidOffer bidForm(String name, double newValue, String apiKey) {
        Form form = new Form();
        form.param("name", name);
        form.param("value", String.valueOf(newValue));

        Response response = post("/bid", Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), apiKey);

        return response.readEntity(BidOffer.class);
    }

    public void sell(Item item, String apiKey) {
        log.debug("Selling an item ");
        post("/offer", Entity.entity(new BidDemand(item.getName(), item.getValue()), MediaType.APPLICATION_JSON_TYPE), apiKey);
        log.debug("item " + item.getName() + " was sent for sale ");
    }

    private Response post(String resourcePath, Entity<Object> entity, String apiKey) {
        Response response = webTarget.path(resourcePath).request()
                .header(AUTHORIZATION, apiKey)
                .post(entity, Response.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Status " + response.getStatus() + " - " + response.readEntity(String.class));
        }
        return response;
    }
}
