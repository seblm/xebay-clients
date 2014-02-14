package fr.xebia.xebay.dto;

public class ItemOffer {
    private String name;
    private double value;

    public ItemOffer() {
    }

    public ItemOffer(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
