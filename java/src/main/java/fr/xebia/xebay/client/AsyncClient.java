package fr.xebia.xebay.client;

import fr.xebia.xebay.Consumer;
import fr.xebia.xebay.api.socket.BidEngineSocketCoder;
import fr.xebia.xebay.api.socket.BidEngineSocketOutput;
import fr.xebia.xebay.domain.BidOffer;
import fr.xebia.xebay.domain.PluginInfo;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@ClientEndpoint(encoders = {BidEngineSocketCoder.class}, decoders = {BidEngineSocketCoder.class})
public class AsyncClient {

    final Session session;
    private Consumer<BidOffer> newBidOfferCallback;
    private Consumer<PluginInfo> pluginInfoCallback;

    public AsyncClient(String hostAndPort, String apiKey) throws IOException, DeploymentException, URISyntaxException {
        URI endpointURI = new URI("ws://" + hostAndPort + "/socket/bidEngine/" + apiKey);
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
        if (pluginInfoCallback != null && message.getNews() != null) {
            pluginInfoCallback.accept(message.getNews());
        }
    }

    @OnError
    @OnClose
    public void onClose() {
    }

    public void onBidOfferChange(Consumer<BidOffer> newBidOfferCallback) {
        this.newBidOfferCallback = newBidOfferCallback;
    }

    public void onPluginInfo(Consumer<PluginInfo> pluginInfoCallback) {
        this.pluginInfoCallback = pluginInfoCallback;
    }
}
