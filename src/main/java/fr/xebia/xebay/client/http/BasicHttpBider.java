package fr.xebia.xebay.client.http;

import fr.xebia.xebay.domain.BidOffer;
import fr.xebia.xebay.domain.User;
import fr.xebia.xebay.dto.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicHttpBider {

    private static final Logger log = LoggerFactory.getLogger("BidClient");

    private RestBidder restBidder;

    private User user;

    public static void main(String[] args) {
        String apiKey = null;
        if (args.length == 1) {
            apiKey = args[0];
        } else {
            System.err.println("Error: no api key has been provided.\n" +
                    "Usage: java " + BasicHttpBider.class.getName() + " API_KEY");
            System.exit(1);
        }

        new BasicHttpBider(apiKey).startBidAuto();
    }

    public BasicHttpBider(String apiKey) {
        restBidder = new RestBidder("http://localhost:8080/rest", apiKey);

        this.user = restBidder.getUserInfo();
    }

    private void startBidAuto() {
        while (true) {
            bidAsYouCan();

            //user must sell item to gain money and bid again
            Item ownedItem = getRandomItem();
            restBidder.sell(new Item(ownedItem.getCategory(), ownedItem.getName(), 100.0));

            while (!hasEnoughMoney()) {
                //waiting he's got sale money !
                sleep();
            }
        }
    }

    private void bidAsYouCan() {
        while (hasEnoughMoney()) {
            bidIfNotMine();
            sleep();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private void bidIfNotMine() {
        BidOffer currentBidOffer = restBidder.getCurrentOffer();

        if (user.getName().equals(currentBidOffer.getOwner())) {
            return;
        }

        if (user.getName().equals(currentBidOffer.getBidder())) {
            return;
        }

        log.debug("Current Bid Offer : " + currentBidOffer.toString());

        double curValue = currentBidOffer.getItem().getValue();
        double increment = curValue * 10 / 100;

        try {
            BidOffer afterBid = restBidder.bid(currentBidOffer.getItem().getName(), curValue + increment);
            log.debug("After Bidding : " + afterBid.toString());

        } catch (Exception e) {
            log.info("Couldn't bid'" + e.getMessage());
        }
    }


    private Item getRandomItem() {
        return user.getItems().stream()
                .filter((item) -> item.getName().contains("an"))
                .findAny().orElseThrow(() -> new RuntimeException("No Item to Sell !!!"));
    }


    private boolean hasEnoughMoney() {
        this.user = restBidder.getUserInfo();
        return user.getBalance() > 20;
    }

}
