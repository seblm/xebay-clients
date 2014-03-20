package fr.xebia.xebay.client.http;

import fr.xebia.xebay.domain.BidOffer;
import fr.xebia.xebay.domain.User;
import fr.xebia.xebay.dto.BidDemand;
import fr.xebia.xebay.dto.Item;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestBidder {
    private static final Logger log = LoggerFactory.getLogger("RestBidder");

    private final WebTarget webTarget;
    private final Client client;
    private final String apiKey;
    private final String target;


    public RestBidder(String target, String apiKey) {

        this.target = target;
        this.client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

        this.webTarget = client.target(this.target).path("/bidEngine");
        this.apiKey = apiKey;

    }

    public String register(String email) {
        return webTarget.path("register").queryParam("email", email).request().get(String.class);
    }

    public User getUserInfo() {

        User user = client.target(target).path("/users/info")
                .request()
                .header(HttpHeaders.AUTHORIZATION, apiKey)
                .get(User.class);
        log.info("User info : " + user.toString());
        return user;
    }

    public BidOffer getCurrentOffer() {
        return webTarget.path("/current").request().get(BidOffer.class);
    }

    public BidOffer bidForm(String name, double newValue) {
        Form form = new Form();
        form.param("name", name);
        form.param("value", String.valueOf(newValue));

        Response response = post("/bid", Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        return response.readEntity(BidOffer.class);
    }


    public void sell(Item item) {
        log.debug("Selling an item ");
        post("/offer", Entity.entity(new BidDemand(item.getName(), item.getValue()), MediaType.APPLICATION_JSON_TYPE));
        log.debug("item " + item.getName() + " was sent for sale ");
    }


    private Response post(String resourcePath, Entity<Object> entity) {
        Response response = webTarget.path(resourcePath).request()
                .header(HttpHeaders.AUTHORIZATION, apiKey)
                .post(entity, Response.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Status " + response.getStatus() + " - " + response.readEntity(String.class));
        }
        return response;
    }


}
