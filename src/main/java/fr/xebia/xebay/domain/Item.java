package fr.xebia.xebay.domain;

import static java.lang.String.format;

public class Item {
    private String name;
    private String category;
    private double value;

    public Item() {
    }

    public Item(String category, String name, double value) {
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

    @Override
    public String toString() {
        return format("[%s] %s", category, name);
    }
}
