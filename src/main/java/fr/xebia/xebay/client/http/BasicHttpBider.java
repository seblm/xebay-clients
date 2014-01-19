package fr.xebia.xebay.client.http;


import fr.xebia.xebay.dto.BidOfferInfo;
import fr.xebia.xebay.dto.UserInfo;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

public class BasicHttpBider {

    private static final Logger log = Logger.getLogger("BidClient");

    private Client client;
    private WebTarget targetBid;
    private String key ;


    public static void main(String[] args) {
        BasicHttpBider bidClient = new BasicHttpBider();
        bidClient.startBidAuto();

    }


    public BasicHttpBider() {
        client = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

        try {
            key = client.target("http://localhost:8080/rest/users/register").queryParam("email", "aaa@eee.com").request().get(String.class);
        } catch (ForbiddenException e) {
            //TODO already register  and no more key --> login/logout
            e.printStackTrace();
        }

        targetBid = client.target("http://localhost:8080/rest/bidEngine");
    }

    private void startBidAuto() {
        int i = 0;
        while(i < 30){
            bid();
            i++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }



    private BidOfferInfo getBidOffer(){
        return targetBid.request().get(BidOfferInfo.class);
    }

    private BidOfferInfo bid(){
        BidOfferInfo currentBidOffer = getBidOffer();

        log.info("Current Bid Offer : " + currentBidOffer.toString());

        double curValue = currentBidOffer.getCurrentValue();
        double increment = curValue + curValue * 10 / 100 ;

        try {
            return bidForm(currentBidOffer.getItemName(), curValue, increment);
        } catch (WebApplicationException e) {
            //no bid
            log.info(e.getMessage());
            return currentBidOffer;
        }
    }

    private BidOfferInfo bidForm(String name, double curValue, double increment) throws WebApplicationException{
        Form form = new Form();
        form.param("name", name);
        form.param("value", String.valueOf(curValue));
        form.param("increment", String.valueOf(increment));


        Response response = targetBid.path("/bid").request()
                .header(HttpHeaders.AUTHORIZATION, key)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), Response.class);
        if(response.getStatus() != 200){
            throw new WebApplicationException(response.readEntity(String.class).toString());
        }
        return response.readEntity(BidOfferInfo.class);

/*
        return targetBid.path("/bid").request()
                .header(HttpHeaders.AUTHORIZATION, key)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), BidOfferInfo.class);
*/
    }

    private boolean hasMonney() {
        UserInfo userInfo = client.target("http://localhost:8080/rest/users/info").queryParam("email", "aaa@eee.com").request().get(UserInfo.class);
        log.info("User info : " + userInfo.toString());

        return userInfo.hasMonney();
    }

}
