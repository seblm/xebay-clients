package fr.xebia.xebay.dto;

public class UserInfo {
    private String name;
    private double balance;

    public UserInfo(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public UserInfo() {
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public boolean hasMonney() {
        return balance > 0;
    }

    @Override
    public String toString() {
        return "Email " + name + "Balance " + balance;
    }
}
