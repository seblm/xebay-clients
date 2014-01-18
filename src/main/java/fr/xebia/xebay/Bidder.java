package fr.xebia.xebay;

import com.google.gson.Gson;

import javax.websocket.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Bidder {

  final RestBidder restBidder;

  final WebSocketBidder webSocketBidder;

  public Bidder() throws IOException, DeploymentException, URISyntaxException {

    restBidder = new RestBidder("http://localhost:8080/rest/");

    webSocketBidder = new WebSocketBidder("http://localhost:8080/ws/");
  }

  public static void main(String[] args) throws DeploymentException, IOException, URISyntaxException {

    Bidder bidder = new Bidder();
    // TODO implement me

  }

}
