package fr.xebia.xebay.dto;

public class BidDemand {
    private String itemName;
    private double value;

    public BidDemand() {
    }

    public BidDemand(String itemName, double value) {
        this.itemName = itemName;
        this.value = value;
    }

    public String getItemName() {
        return itemName;
    }

    public double getValue() {
        return value;
    }
}
