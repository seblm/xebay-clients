package fr.xebia.xebay.domain;

public class BidOffer {
    private Item item;
    private long timeToLive;
    private String userName;
    private String avatarUrl;
    private boolean expired;

    public BidOffer() {
    }

    public BidOffer(String itemCategory, String itemName, double itemValue, String userName, int timeToLive, String avatarUrl, boolean expired) {
        this.item = new Item(itemCategory, itemName, itemValue);
        this.userName = userName;
        this.timeToLive = timeToLive;
        this.avatarUrl = avatarUrl;
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

    public String getAvatarUrl() {
        return avatarUrl;
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
