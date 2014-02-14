package fr.xebia.xebay.client.http;

import fr.xebia.xebay.dto.BidOfferInfo;
import fr.xebia.xebay.dto.ItemOffer;
import fr.xebia.xebay.dto.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicHttpBider {

    private static final Logger log = LoggerFactory.getLogger("BidClient");

    private RestBidder restBidder;

    private UserInfo userInfo;

    public static void main(String[] args){
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

    public BasicHttpBider(String apiKey){
        restBidder = new RestBidder("http://localhost:8080/rest", apiKey);

        this.userInfo = restBidder.getUserInfo();

    }

    private void startBidAuto() {
        while (true) {
            bidAsYouCan();

            //user must sell item to gain money and bid again
            ItemOffer ownedItem = getRandomItem();
            restBidder.sell(new ItemOffer(ownedItem.getName(), 100.0));

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
        BidOfferInfo currentBidOffer = restBidder.getCurrentOffer();

        if (userInfo.getName().equals(currentBidOffer.getFutureBuyerName())) {
            return;
        }

        log.debug("Current Bid Offer : " + currentBidOffer.toString());

        double curValue = currentBidOffer.getCurrentValue();
        double increment = curValue * 10 / 100;

        try {
            BidOfferInfo afterBid = restBidder.bidForm(currentBidOffer.getItemName(), curValue, increment);
            log.debug("After Bidding : " + afterBid.toString());

        } catch (Exception e) {
            log.info("Couldn't bid'" + e.getMessage());
        }
    }


    private ItemOffer getRandomItem() {
        return userInfo.getItems().stream()
                .filter((item) -> item.getName().contains("an"))
                .findAny().orElseThrow(() -> new RuntimeException("No Item to Sell !!!"));
    }


    private boolean hasEnoughMoney() {
        this.userInfo = restBidder.getUserInfo();
        return userInfo.getBalance() > 20;
    }

}
