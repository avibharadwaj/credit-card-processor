package com.example.creditcarddesign;
import java.util.*;
import java.io.*;

public class CSVFileParser extends FileParser{

    static CreditCardFactory creditCardFactory = new CreditCardFactory();
    static ArrayList<String> finalOutput = new ArrayList<>();

    @Override
    protected ArrayList<String> readFile(String fileName){
        System.out.println("Reading CSV file: "+fileName);
        String path = fileName;
        try {
            Scanner reader = new Scanner(new File(path));
            reader.nextLine();
            while (reader.hasNextLine()) {
                String data = reader.nextLine();
                //Split comma separated data
                String[] tokenArray = data.split("\\s*,\\s*");
                String type = processData(tokenArray[0]);

                CreditCard creditCard = creditCardFactory.createCreditCard(type); //instantiation of credit card
                String output = creditCard.getCreditCardType(tokenArray[0],tokenArray[2],type);
                finalOutput.add(output);
                
            }
            reader.close();
            return finalOutput;

        } catch (FileNotFoundException e) {
            System.out.println("Scanner error");
            e.printStackTrace();
        }
        return null;
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
        return fileName.endsWith(".csv");
    }

    @Override
    protected void writeOutput(ArrayList<String> finalOutput){
        System.out.println(finalOutput);
        String csvFile = "output_file.csv";
        FileWriter writer = null;
        try{
            writer = new FileWriter(csvFile);
            writer.append("card number");
            writer.append(",");
            writer.append("type");
            writer.append("\n");

            for(String creditCard: finalOutput){
                String[] creditCardFields = creditCard.split(",");
                String cardNumber = creditCardFields[0];
                String cardType = creditCardFields[1];
                writer.append(cardNumber);
                writer.append(",");
                writer.append(cardType);
                writer.append("\n");
            }
        } catch(IOException e){
            e.printStackTrace();
        } finally {
            try{
                writer.flush();
                writer.close();  
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}

