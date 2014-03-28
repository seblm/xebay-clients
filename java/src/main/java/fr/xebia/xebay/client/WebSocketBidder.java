package fr.xebia.xebay.client;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Consumer;

@ClientEndpoint
public class WebSocketBidder {

    final Session session;
    private final Consumer<String> callback;

    public WebSocketBidder(String endpoint, Consumer<String> callback) throws IOException, DeploymentException, URISyntaxException {
        this.callback = callback;

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
        callback.accept(message);
    }

    @OnError
    @OnClose
    public void onClose() {
        // TODO implement me
    }

}
