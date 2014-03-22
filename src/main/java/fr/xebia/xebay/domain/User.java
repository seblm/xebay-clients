package fr.xebia.xebay.domain;

import java.util.Set;

public class User {
    private String name;
    private String key;
    private String avatarUrl;
    private double balance;
    private Set<Item> items;

    public User(String name, String key, String avatarUrl, double balance, Set<Item> items) {
        this.name = name;
        this.key = key;
        this.avatarUrl = avatarUrl;
        this.balance = balance;
        this.items = items;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public double getBalance() {
        return balance;
    }

    public Set<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Item item : items) {
            stringBuilder.append(item.toString());
        }
        return "User{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", balance=" + balance +
                ", items=" + stringBuilder.toString() +
                '}';
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
