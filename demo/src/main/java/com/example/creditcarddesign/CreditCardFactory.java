package com.example.creditcarddesign;

public class CreditCardFactory {
    public CreditCard createCreditCard(String creditCard){
     if(creditCard==null || creditCard.isEmpty())
         return null;
     switch(creditCard){
         case "AMEX":
             return new AmExCC();
         case "MasterCard":
             return new MasterCC();
         case "Visa":
             return new VisaCC();
         case "Discover":
             return new DiscoverCC();
         default:
             return new Invalid();
     }
    }
 }
 