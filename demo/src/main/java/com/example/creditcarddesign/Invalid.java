package com.example.creditcarddesign;

public class Invalid implements CreditCard {
    String accountNumber;
    String accountName;
    String type;
    @Override
    public String getCreditCardType(String accountNumber, String accountName, String type){
        this.accountName=accountName;
        this.accountNumber=accountNumber;
        this.type=type;
        return accountNumber+","+type;
    } 
}
