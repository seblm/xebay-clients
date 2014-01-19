package fr.xebia.xebay.client;

import com.google.gson.Gson;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint
public class WebSocketBidder {

  static final Gson gson = new Gson();

  final Session session;

  public WebSocketBidder(String endpoint) throws IOException, DeploymentException, URISyntaxException {

    URI endpointURI = new URI(endpoint);
    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    session = container.connectToServer(this, endpointURI);
  }

  @OnOpen
  public void onOpen() {
    // TODO implement me
  }

  @OnMessage
  public void onMessage(String message) {
    // TODO implement me
  }

  @OnError
  @OnClose
  public void onClose() {
    // TODO implement me
  }

}
