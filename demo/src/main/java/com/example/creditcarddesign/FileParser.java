package com.example.creditcarddesign;
import java.util.*;
public abstract class FileParser {
    public final void parseFile(String fileName){ //template algorithm which each file parser type overrides
        String input="";
        ArrayList<String> finalOutput = new ArrayList<>();
        if(isValidFileType(fileName)){
            finalOutput=readFile(fileName);
            processData(input);
            writeOutput(finalOutput);
        } else {
            System.out.println("Invalid file type");
        }
    }

    protected abstract ArrayList<String> readFile(String fileName);

    protected abstract String processData(String line);

    protected abstract boolean isValidFileType(String fileName);

    protected abstract void writeOutput(ArrayList<String> fileOutput);

}
