package com.example.creditcarddesign;

import java.io.File;
import java.util.*;

public class App {
    static CreditCardFactory creditCardFactory = new CreditCardFactory();
    static ArrayList<String> finalOutput = new ArrayList<>();
    public static void main(String[] args) throws Exception {
        //get input from user
            Scanner myObj = new Scanner(System.in);
            System.out.println("Enter a file name");
            String fileName = myObj.nextLine();

            myObj.close();
            
            if(isFile(fileName)){

            String extension = getExtension(fileName);
            //get filepath
            if(extension.equals("csv")){
                FileParser parser = new CSVFileParser();
                parser.parseFile(fileName);
            } else if(extension.equals("json")){
                FileParser parser = new JSONFileParser();
                parser.parseFile(fileName);
            } else if(extension.equals("xml")){
                FileParser parser = new XMLFileParser();
                parser.parseFile(fileName);
            }
        } else {
            System.out.println("File may be incorrect or may not exist");
        }
    }
    
    public static boolean isFile(String filename) {
        File file = new File(filename);
        return file.isFile();
    }

    public static String getExtension(String filename) {
        int dotIndex = filename.lastIndexOf(".");
        if (dotIndex == -1) {
            return "";
        } else {
            return filename.substring(dotIndex + 1);
        }
    }
}
