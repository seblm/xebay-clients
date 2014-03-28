package fr.xebia.xebay.client.http;

import fr.xebia.xebay.client.WebSocketBidder;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URISyntaxException;

import static java.lang.String.format;

public class BasicHttpBider {

    public BasicHttpBider(String apiKey) {
        RestBidder restBidder = new RestBidder("http://localhost:8080/rest");

        try {
            new WebSocketBidder(format("ws://localhost:8080/socket/bidEngine/%s", apiKey), System.out::println);
        } catch (IOException | DeploymentException | URISyntaxException e) {
            e.printStackTrace();
        }
        System.out.println(restBidder.getCurrentOffer());

        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // ignore
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Error: no api key has been provided.\n" +
                    "Usage: java " + BasicHttpBider.class.getName() + " API_KEY");
            System.exit(1);
        }

        new BasicHttpBider(args[0]);
    }
}
