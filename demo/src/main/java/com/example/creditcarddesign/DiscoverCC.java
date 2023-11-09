package com.example.creditcarddesign;

public class DiscoverCC implements CreditCard {
    String accountNumber;
    String accountName;
    String type;
    @Override
    public String getCreditCardType(String accountNumber, String accountName, String type){
        this.accountName=accountName;
        this.accountNumber=accountNumber;
        this.type=type;
        // System.out.println("---This is Discover---");
        
        return accountNumber+",Discover";
    }
}