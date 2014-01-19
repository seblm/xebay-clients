package fr.xebia.xebay.client.http;

import com.google.gson.Gson;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.net.URISyntaxException;

public class RestBidder {

  static final Gson gson = new Gson();

  final WebTarget webTarget;

  public RestBidder(String target) throws URISyntaxException {

    URI targetURI = new URI(target);
    Client client = ClientBuilder.newClient();
    webTarget = client.target(targetURI);
  }

  public String register(String email) {
    return webTarget.path("register").queryParam("email", email).request().get(String.class);
  }

  // TODO public void send(Bid bid);
  public void getCurrentOffer(String key) {
    webTarget.request().header(HttpHeaders.AUTHORIZATION, key).accept(MediaType.APPLICATION_JSON).get();
  }

  // TODO public void sell(Item item);

}
