package fr.xebia.xebay.dto;

public class ItemOffer {
    private String name;
    private String category;
    private double value;

    public ItemOffer() {
    }

    public ItemOffer(String category, String name, double value) {
        this.name = name;
        this.category = category;
        this.value = value;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }
}
