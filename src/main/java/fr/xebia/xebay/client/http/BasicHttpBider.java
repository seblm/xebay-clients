package fr.xebia.xebay.client.http;

import fr.xebia.xebay.dto.BidOfferInfo;
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

public class BasicHttpBider {

    private static final Logger log = LoggerFactory.getLogger("BidClient");

    //TODO your api key
    private String apiKey = "XPD3993XyOVs5FSo";
    private final Client client;
    private final WebTarget targetBid;

    private String name;

    public static void main(String[] args) {
        String userKey = null;
        if(args.length == 1){
            userKey = args[0];
        }
        BasicHttpBider bidClient = new BasicHttpBider(userKey);

        bidClient.startBidAuto();
    }

    public BasicHttpBider(String userApiKey) {
        if(null != userApiKey){
            apiKey = userApiKey;
        }
        this.client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
        this.targetBid = client.target("http://localhost:8080/rest/bidEngine");

        initUserInfo();
    }

    private void startBidAuto() {
        while (userCanBid()) {
            bidIfNotMine();
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private BidOfferInfo getCurrentBidOffer() {
        Response response = targetBid.path("/current").request().get(Response.class);
        return response.readEntity(BidOfferInfo.class);
    }

    private void bidIfNotMine() {
        BidOfferInfo currentBidOffer = getCurrentBidOffer();

        if (name.equals(currentBidOffer.getFutureBuyerName())) {
            return;
        }

        log.debug("Current Bid Offer : " + currentBidOffer.toString());

        double curValue = currentBidOffer.getCurrentValue();
        double increment = curValue + (curValue * 10 / 100);

        try {
            BidOfferInfo afterBid = bidForm(currentBidOffer.getItemName(), curValue, increment);
            log.debug("After Bidding : " + afterBid.toString());

        } catch (Exception e) {
            log.info("Couldn't bid'" + e.getMessage());
        }
    }

    private BidOfferInfo bidForm(String name, double curValue, double increment) {
        Form form = new Form();
        form.param("name", name);
        form.param("value", String.valueOf(curValue));
        form.param("increment", String.valueOf(increment));

        Response response = targetBid.path("/bid").request()
                .header(HttpHeaders.AUTHORIZATION, apiKey)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Response.class);
        if (response.getStatus() != 200) {
            throw new RuntimeException("Status " + response.getStatus() + " - " + response.readEntity(String.class).toString());
        }
        return response.readEntity(BidOfferInfo.class);
    }

    private boolean userCanBid() {
        UserInfo userInfo = getUserInfo();

        return userInfo.hasMonney();
    }

    private UserInfo getUserInfo() {
        UserInfo userInfo = client.target("http://localhost:8080/rest/users/info")
                .request()
                .header(HttpHeaders.AUTHORIZATION, apiKey)
                .get(UserInfo.class);
        log.info("User info : " + userInfo.toString());
        return userInfo;
    }

    private void initUserInfo() {
        UserInfo userInfo = getUserInfo();
        this.name = userInfo.getName();
    }
}
