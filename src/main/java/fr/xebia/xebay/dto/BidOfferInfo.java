package fr.xebia.xebay.dto;

import java.util.logging.Logger;

public class BidOfferInfo {
    private static final Logger log = Logger.getLogger("BidOffer");

    private String itemName;
    private double currentValue;
    private double initialValue;
    private String ownerEmail;
    private String futureBuyerEmail;
    private int timeToLive;

    public BidOfferInfo() {
    }

    public BidOfferInfo(String itemName, double currentValue, double initialValue, String ownerEmail, String futurBuyerEmail, int timeToLive) {
        this.itemName = itemName;
        this.currentValue = currentValue;
        this.initialValue = initialValue;
        this.ownerEmail = ownerEmail;
        this.futureBuyerEmail = futurBuyerEmail;
        this.timeToLive = timeToLive;
    }

    public double getCurrentValue() {
        return currentValue;
    }


    public int getTimeToLive() {
        return timeToLive;
    }

    public String getItemName() {
        return itemName;
    }

    public double getInitialValue() {
        return initialValue;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public String getFutureBuyerEmail() {
        return futureBuyerEmail;
    }

    @Override
    public String toString() {
        return "BidOfferInfo{" +
                "itemName='" + itemName + '\'' +
                ", currentValue=" + currentValue +
                ", initialValue=" + initialValue +
                ", ownerEmail='" + ownerEmail + '\'' +
                ", futurBuyerEmail='" + futureBuyerEmail + '\'' +
                ", timeToLive=" + timeToLive +
                '}';
    }
}
