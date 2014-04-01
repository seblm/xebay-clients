package fr.xebia.xebay.client;

import fr.xebia.xebay.api.socket.BidEngineSocketCoder;
import fr.xebia.xebay.api.socket.BidEngineSocketOutput;
import fr.xebia.xebay.domain.BidOffer;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.function.Consumer;

@ClientEndpoint(encoders = {BidEngineSocketCoder.class}, decoders = {BidEngineSocketCoder.class})
public class AsyncClient {

    final Session session;
    private Consumer<BidOffer> newBidOfferCallback;
    private Consumer<String> infoCallback;

    public AsyncClient(String endpoint) throws IOException, DeploymentException, URISyntaxException {
        URI endpointURI = new URI(endpoint);
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        session = container.connectToServer(this, endpointURI);
    }

    @OnOpen
    public void onOpen() {
    }

    @OnMessage
    public void onMessage(BidEngineSocketOutput message) {
        if (newBidOfferCallback != null && message.getBidOffer() != null) {
            newBidOfferCallback.accept(message.getBidOffer());
        }
        if (infoCallback != null && message.getInfo() != null) {
            infoCallback.accept(message.getInfo());
        }
    }

    @OnError
    @OnClose
    public void onClose() {
    }

    public void onBidOfferChange(Consumer<BidOffer> newBidOfferCallback) {
        this.newBidOfferCallback = newBidOfferCallback;
    }

    public void onInfo(Consumer<String> infoCallback) {
        this.infoCallback = infoCallback;
    }
}
