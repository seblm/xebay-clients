package fr.xebia.xebay.dto;

public class BidOfferInfo {
    private String itemName;
    private double currentValue;
    private double initialValue;
    private String ownerName;
    private String futureBuyerName;
    private int timeToLive;

    public BidOfferInfo() {
    }

    public BidOfferInfo(String itemName, double currentValue, double initialValue, String ownerName, String futurBuyerEmail, int timeToLive) {
        this.itemName = itemName;
        this.currentValue = currentValue;
        this.initialValue = initialValue;
        this.ownerName = ownerName;
        this.futureBuyerName = futurBuyerEmail;
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

    public String getOwnerName() {
        return ownerName;
    }

    public String getFutureBuyerName() {
        return futureBuyerName;
    }

    @Override
    public String toString() {
        return "BidOfferInfo{" +
                "itemName='" + itemName + '\'' +
                ", currentValue=" + currentValue +
                ", initialValue=" + initialValue +
                ", ownerName='" + ownerName + '\'' +
                ", futureBuyerName='" + futureBuyerName + '\'' +
                ", timeToLive=" + timeToLive +
                '}';
    }
}
