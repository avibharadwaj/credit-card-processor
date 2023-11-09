package com.example.creditcarddesign;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLFileParser extends FileParser {
    static CreditCardFactory creditCardFactory = new CreditCardFactory();
    static ArrayList<String> finalOutput = new ArrayList<>();

    @Override
    protected ArrayList<String> readFile(String fileName){
        String path = fileName;
        try {
            //creating a constructor of file class and parsing an XML file  
            File file = new File(path);  
            //an instance of factory that gives a document builder  
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
            //an instance of builder to parse the specified xml file  
            DocumentBuilder db = dbf.newDocumentBuilder();  
            Document doc = db.parse(file);  
            doc.getDocumentElement().normalize();  
            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
            NodeList nodeList = doc.getElementsByTagName("CARD");  
            // nodeList is not iterable, so we are using for loop  
            for (int itr = 0; itr < nodeList.getLength(); itr++)   
            {  
            Node node = nodeList.item(itr);  
            if (node.getNodeType() == Node.ELEMENT_NODE)   
                {  
                    Element eElement = (Element) node;  
                    String cardNumber = eElement.getElementsByTagName("CARD_NUMBER").item(0).getTextContent();
                    String holderName = eElement.getElementsByTagName("CARD_HOLDER_NAME").item(0).getTextContent();
                    String type = processData(cardNumber);
                    CreditCard creditCard = creditCardFactory.createCreditCard(type); //instantiation of credit card
                    String output = creditCard.getCreditCardType(cardNumber,holderName,type);
                    finalOutput.add(output);  
                }  
            }  
        } catch(Exception e){
            e.printStackTrace();
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
        return fileName.endsWith(".xml");
    }
    
    @Override
    protected void writeOutput(ArrayList<String> finalOutput){
        System.out.println(finalOutput);
        String xmlFile = "output_file.xml";
        FileWriter writer = null;

        try {
            writer = new FileWriter(xmlFile);

            // Write XML header
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n");
            writer.write("<CARDS>\n");

            // Write XML data
            for (String creditCard : finalOutput) {
                String[] creditCardFields = creditCard.split(",");
                String cardNumber = creditCardFields[0];
                String cardType = creditCardFields[1];
                writer.write("  <CARD>\n");
                writer.write("    <CARD_NUMBER>" + cardNumber + "</CARD_NUMBER>\n");
                writer.write("    <CARD_TYPE>" + cardType + "</CARD_TYPE>\n");
                writer.write("  </CARD>\n");
            }

            // Write XML footer
            writer.write("</CARDS>\n");

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

