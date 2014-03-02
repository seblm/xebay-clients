package fr.xebia.xebay.dto;

import java.util.Set;

public class UserInfo {
    private String name;
    private String avatarUrl;
    private double balance;
    private Set<Item> items;


    public UserInfo(String name, String avatarUrl, double balance, Set<Item> items) {
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.balance = balance;
        this.items = items;
    }

    public UserInfo() {
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public Set<Item> getItems() {
        return items;
    }

    public boolean hasMonney() {
        return balance > 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(Item item : items){
            stringBuilder.append(item.toString());
        }
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", balance=" + balance +
                ", items=" + stringBuilder.toString() +
                '}';
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }
}
