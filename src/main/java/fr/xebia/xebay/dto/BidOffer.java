package fr.xebia.xebay.dto;

public class BidOffer {
    private Item item;
    private long timeToLive;
    private String userName;
    private boolean expired;

    public BidOffer() {
    }

    public BidOffer(String itemCategory, String itemName, double itemValue, String userName, int timeToLive, boolean expired) {
        this.item = new Item(itemCategory, itemName, itemValue);
        this.userName = userName;
        this.timeToLive = timeToLive;
        this.expired = this.expired;
    }

    public Item getItem() {
        return item;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isExpired() {
        return expired;
    }

    @Override
    public String toString() {
        return "BidOffer{" +
                "item=" + item +
                ", timeToLive=" + timeToLive +
                ", userName='" + userName + '\'' +
                ", expired=" + expired +
                '}';
    }
}
