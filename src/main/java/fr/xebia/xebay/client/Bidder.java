package fr.xebia.xebay.client;

import fr.xebia.xebay.client.http.RestBidder;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;

public class Bidder {

  final RestBidder restBidder;

  final WebSocketBidder webSocketBidder;

  public Bidder() throws IOException, DeploymentException, URISyntaxException {

    restBidder = new RestBidder("http://localhost:8080/rest/", "falseApiKey");

    webSocketBidder = new WebSocketBidder("http://localhost:8080/ws/");
  }

  public static void main(String[] args) throws DeploymentException, IOException, URISyntaxException {

    Bidder bidder = new Bidder();
    // TODO implement me

  }

}
