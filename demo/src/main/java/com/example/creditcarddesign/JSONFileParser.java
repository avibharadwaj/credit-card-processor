package com.example.creditcarddesign;
import java.util.*;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class JSONFileParser extends FileParser {
    static CreditCardFactory creditCardFactory = new CreditCardFactory();
    static ArrayList<String> finalOutput = new ArrayList<>();

    @Override
    protected ArrayList<String> readFile(String fileName){
        System.out.println("Reading JSON file: "+fileName);
        String path = fileName;
        try{
            FileReader reader = new FileReader(path);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(reader);
            JSONArray cards = (JSONArray) jsonObject.get("cards");

            for(int i=0;i<cards.size();++i){
                String entries = cards.get(i).toString();
                String[] eachCardEntry = entries.split(",");

                //NumberEntry
                String numberEntry = eachCardEntry[1];
                String[] split1 = numberEntry.split(":");

                //Extract cardNumber
                String cardNumber = "";
                String type = "";
                if(split1[0].replaceAll("^\"|\"$", "").equals("cardNumber")){
                    String replace1 = split1[1].replaceAll("}", "");
                    String replace2 = replace1.replaceAll("\\\\", "");
                    cardNumber = replace2.replaceAll("^\"|\"$", "");
                    // type = processData(replace2);

                } else {
                    cardNumber = "";
                }
            
                //HolderEntry
                String holderEntry = eachCardEntry[0];
                String[] split2 = holderEntry.split(":");

                //Extract CardHolder name
                String holderName = "";
                String replace1 = split2[1].replaceAll("}", "");
                String replace2 = replace1.replaceAll("\\\\", "");
                holderName = replace2;                    


                //Get Card Type
                type = processData(cardNumber);
                CreditCard creditCard = creditCardFactory.createCreditCard(type); //instantiation of credit card
                String output = creditCard.getCreditCardType(cardNumber,holderName,type);
                finalOutput.add(output);
                
            }

            
        } catch(Exception e){
            System.out.println("Scanner error: "+e);
        }

        return finalOutput;
    }

    @Override
    protected String processData(String input){
        String cardNumber = input;
        String type = "";
        //check length to not exceed more than 19 digits
        if(cardNumber==null || cardNumber.length()==0){
            type="Invalid: empty/null card number";
        }
        else if(cardNumber.length()<=19 && Character.isDigit(cardNumber.charAt(cardNumber.length()-1))){
        //check for MasterCard
            if(cardNumber.charAt(0)=='5' && cardNumber.length()==16){
                if(cardNumber.charAt(1)>='1' && cardNumber.charAt(1)<='5'){
                    try{
                        Long.parseLong(cardNumber);
                        type="MasterCard";
                    } catch(NumberFormatException nfe){
                        type = "Invalid: non numeric characters";
                    }
                } else {
                    type = "Invalid: not a possible card number";
                }
            }

        //check for Visa
            else if(cardNumber.length()==13 || (cardNumber.length()==16 && !cardNumber.substring(0, 4).equals("6011"))){
                if(cardNumber.charAt(0)=='4'){
                    try{
                        Long.parseLong(cardNumber);
                        type="Visa";
                    } catch(NumberFormatException nfe){
                        type = "Invalid: non numeric characters";
                    }
                } else {
                    type = "Invalid: not a possible card number";
                }
            }
        //check for Amex
            else if(cardNumber.length()==15 && cardNumber.charAt(0)=='3'){
                if(cardNumber.charAt(1)=='4'||cardNumber.charAt(1)=='7'){
                    try{
                        Long.parseLong(cardNumber);
                        type="AMEX";
                    } catch(NumberFormatException nfe){
                        type = "Invalid: non numeric characters";
                    }                   
                } else {
                    type = "Invalid: not a possible card number";
                }

            }
        //check for Discover
            else if(cardNumber.substring(0, 4).equals("6011")){
                if(cardNumber.length()==16){
                    try{
                        Long.parseLong(cardNumber);
                        type="Discover";
                    } catch(NumberFormatException nfe){
                        type = "Invalid: non numeric characters";
                    }                         
                } else {
                    type = "Invalid: not a possible card number";
                }
            }
        } else {
            type="Invalid: more than 19 digits";
        }

        return type;
    }

    @Override
    protected boolean isValidFileType(String fileName){
        return fileName.endsWith(".json");
    }

    @Override
    protected void writeOutput(ArrayList<String> finalOutput){
        System.out.println(finalOutput);
        String jsonFile = "output_file.json";
        FileWriter writer = null;

        try {
            writer = new FileWriter(jsonFile);

            // Create JSON array and objects
            JSONArray jsonArray = new JSONArray();
            for (String creditCard : finalOutput) {
                String[] creditCardFields = creditCard.split(",");
                String cardNumber = creditCardFields[0];
                String cardType = creditCardFields[1];
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("cardNumber", cardNumber);
                jsonObject.put("cardType", cardType);
                jsonArray.add(jsonObject);
            }

            // Write JSON to file
            writer.write(jsonArray.toJSONString().replace("},", "},\n"));

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}
