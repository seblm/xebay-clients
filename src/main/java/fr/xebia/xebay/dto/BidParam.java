package fr.xebia.xebay.dto;

public class BidParam {

    private String itemName;
    private double value;

    public BidParam() {
    }

    public BidParam(String itemName, double curValue, double increment) {
        this.itemName = itemName;
        this.value = curValue;
    }

    public String getItemName() {
        return itemName;
    }

    public double getValue() {
        return value;
    }

}
