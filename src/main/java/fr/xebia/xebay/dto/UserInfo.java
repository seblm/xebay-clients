package fr.xebia.xebay.dto;


import java.util.Set;

public class UserInfo {
    private String email;
    private double balance;
    private Set<String> items;

    public UserInfo(String email, double balance, Set<String> items) {
        this.email = email;
        this.balance = balance;
        this.items = items;
    }



    public UserInfo() {
    }


    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }

    public boolean hasMonney(){
        return balance > 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Email ").append(email);
        stringBuilder.append("Balance ").append(balance);
        stringBuilder.append("Items ").append(items.stream().toString());
        return stringBuilder.toString();
    }
}
