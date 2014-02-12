package fr.xebia.xebay.dto;


public class UserInfo {
    private String email;
    private double balance;

    public UserInfo(String email, double balance) {
        this.email = email;
        this.balance = balance;
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
        return stringBuilder.toString();
    }
}
