package fr.xebia.xebay.client.http;

import com.google.gson.Gson;
import fr.xebia.xebay.dto.BidOfferInfo;
import fr.xebia.xebay.dto.ItemOffer;
import fr.xebia.xebay.dto.UserInfo;
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


    static final Gson gson = new Gson();

    private final WebTarget webTarget;
    private final Client client;
    private final String apiKey;
    private final String target;


    public RestBidder(String target, String apiKey){

        this.target = target;
        this.client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

        this.webTarget = client.target(this.target).path("/bidEngine");
        this.apiKey = apiKey;

    }

    public String register(String email) {
        return webTarget.path("register").queryParam("email", email).request().get(String.class);
    }

    public UserInfo getUserInfo() {

        UserInfo userInfo = client.target(target).path("/users/info")
                .request()
                .header(HttpHeaders.AUTHORIZATION, apiKey)
                .get(UserInfo.class);
        log.info("User info : " + userInfo.toString());
        return userInfo;
    }

    public BidOfferInfo getCurrentOffer() {
        return webTarget.path("/current").request().get(BidOfferInfo.class);
    }

    public BidOfferInfo bidForm(String name, double newValue) {
        Form form = new Form();
        form.param("name", name);
        form.param("value", String.valueOf(newValue));

        Response response = post("/bid", Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));

        return response.readEntity(BidOfferInfo.class);
    }


    public void sell(ItemOffer item) {
        log.debug("Selling an item ");
        post("/offer", Entity.entity(item, MediaType.APPLICATION_JSON_TYPE));
        log.debug("item " + item.getName() + " was sent for sale ");
    }


    private Response post(String resourcePath, Entity<Object> entity){
        Response response = webTarget.path(resourcePath).request()
                .header(HttpHeaders.AUTHORIZATION, apiKey)
                .post(entity, Response.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Status " + response.getStatus() + " - " + response.readEntity(String.class));
        }
        return response;
    }


}
